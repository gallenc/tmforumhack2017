/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2002-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.plugins.mqttclient;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.opennms.plugins.messagenotifier.MessageNotification;
import org.opennms.plugins.messagenotifier.MessageNotificationClient;
import org.opennms.plugins.messagenotifier.MessageNotificationClientQueueImpl;
import org.opennms.plugins.messagenotifier.MessageNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientImpl implements MqttCallback, MessageNotifier {
	private static final Logger LOG = LoggerFactory.getLogger(MQTTClientImpl.class);

	// connectionRetryInterval   interval (ms) before re attempting connection.
	private Integer connectionRetryInterval=30000;
	private String brokerUrl;
	private String password;
	private String userName;

	private AtomicBoolean m_useMqtt = new AtomicBoolean(false); // use by default
	private AtomicInteger reconnectionCount = new AtomicInteger(0);

	// Private instance variables
	private MqttAsyncClient 	client;
	private MqttConnectOptions 	conOpt;
	private boolean clean=true;

	private AtomicBoolean clientConnected = new AtomicBoolean(false); 

	private Thread m_connectionRetryThread=null;

	private Set<MessageNotificationClient> messageNotificationClientList = Collections.synchronizedSet(new HashSet<MessageNotificationClient>());

	private Set<MQTTTopicSubscription> topicList = Collections.synchronizedSet(new HashSet<MQTTTopicSubscription>());

	/**
	 * adds new MessageNotificationClient to list of clients which will be sent database notifications
	 * @param MessageNotificationClient
	 */
	@Override
	public void addMessageNotificationClient(MessageNotificationClient messageNotificationClient){
		LOG.debug("adding messageNotificationClient:"+messageNotificationClient.toString());
		messageNotificationClientList.add(messageNotificationClient);
	}

	/**
	 * removes messageNotificationClient from list of clients which will be sent database notifications
	 * @param messageNotificationClient
	 */
	@Override
	public void removeMessageNotificationClient(MessageNotificationClient messageNotificationClient){
		LOG.debug("removing messageNotificationClient:"+messageNotificationClient.toString());
		messageNotificationClientList.remove(messageNotificationClient);
	}

	/**
	 * adds a list of subscriptions which will be subscribed when the class connects to the broker
	 * @param topicList
	 */
	public void setTopicList(Set<MQTTTopicSubscription> topicList){
		this.topicList.addAll(topicList);
	}

	public boolean isClientConnected() {
		return clientConnected.get();
	}
	

	public String getUseMqtt() {
		return Boolean.toString(m_useMqtt.get());
	}

	/**
	 * turns off or on mqtt
	 * @param if true mqtt will try to connect to broker. If false, mqtt will not be used
	 */
	public void setUseMqtt(String useMqtt) {
		this.m_useMqtt.set(Boolean.parseBoolean(useMqtt));
	}

	/**
	 * @param brokerUrl the url to connect to
	 * @param clientId the client id to connect with
	 * @param userName the username to connect with
	 * @param password the password for the user
	 * @param connectionRetryInterval   interval (ms) before re attempting connection.
	 */
	public MQTTClientImpl(String brokerUrl, String clientId, String userName, String password, String connectionRetryInterval) {
		this.brokerUrl = brokerUrl;
		this.userName = userName;
		this.password = password;
		this.connectionRetryInterval=Integer.parseInt(connectionRetryInterval);

		MemoryPersistence persistence = new MemoryPersistence();

		try {
			// Construct the connection options object that contains connection parameters
			// such as cleanSession and LWT
			conOpt = new MqttConnectOptions();
			conOpt.setCleanSession(clean);
			if(password != null ) {
				conOpt.setPassword(this.password.toCharArray());
			}
			if(userName != null) {
				conOpt.setUserName(this.userName);
			}

			// Construct a non-blocking MQTT client instance
			client = new MqttAsyncClient(this.brokerUrl,clientId, persistence);

			// Set this wrapper as the callback handler
			client.setCallback(this);

		} catch (MqttException e) {
			LOG.error("Unable to set up MQTT client",e);
			throw new RuntimeException("Unable to set up MQTT client",e);
		}
	}

	/**
	 * Connect to the MQTT server
	 * issue a non-blocking connect and then use the token to wait until the
	 * connect completes.
	 * @return true if connected, false if not
	 */
	public synchronized boolean connect(){
		if (client.isConnected()) {
			clientConnected.set(true);
			return true;
		}
		LOG.debug("Connecting to "+brokerUrl + " with client ID "+client.getClientId()
				+" (number of connection attempts since start="
				+ reconnectionCount.incrementAndGet()+")");
		IMqttToken conToken;
		try {
			conToken = client.connect(conOpt,null,null);
			conToken.waitForCompletion();
		} catch (MqttException e1) {
			// An exception is thrown if connect fails.
			LOG.error("failed to connect to MQTT broker:"+ brokerUrl ,e1);
			clientConnected.set(false);
			return false;
		}
		LOG.debug("Connected to MQTT broker");
		clientConnected.set(true);
		return true;
	}

	/**
	 * Synchronously Publish / send a message to an MQTT server. Call returns once message has been sent
	 * @param topicName the name of the topic to publish to
	 * @param qos the quality of service to delivery the message at (0,1,2)
	 * @param payload the set of bytes to send to the MQTT server
	 */
	public void publishSynchronous(String topicName, int qos, byte[] payload) {
		if(! clientConnected.get()){
			if (LOG.isDebugEnabled()) LOG.debug("client disconnected. not publishing message");
			return; 
		}

		// Construct the message to send
		MqttMessage message = new MqttMessage(payload);
		message.setQos(qos);

		if (LOG.isDebugEnabled()){
			String time = new Timestamp(System.currentTimeMillis()).toString();
			LOG.debug("Publishing synchronous message at: "+time+ " to topic \""+topicName+"\" qos "+qos);
		}	
		// Send the message to the server, control is returned as soon
		// as the MQTT client has accepted to deliver the message.
		// Use the delivery token to wait until the message has been
		// delivered

		try {
			IMqttDeliveryToken pubToken = client.publish(topicName, message, null, null);
			pubToken.waitForCompletion();
		} catch (MqttException e) {
			throw new RuntimeException("problem synchronously publishing message.",e);
		} 	
		LOG.debug("Published");

	}

	/**
	 * Asynchronously Publish / send a message to an MQTT server. Call returns as soon as message scheduled for sending
	 * @param topicName the name of the topic to publish to
	 * @param qos the quality of service to delivery the message at (0,1,2)
	 * @param payload the set of bytes to send to the MQTT server
	 */
	public void publishAsynchronous(String topicName, int qos, byte[] payload) {
		if(! clientConnected.get()){
			if (LOG.isDebugEnabled()) LOG.debug("client disconnected. not publishing message");
			return; 
		}

		if (LOG.isDebugEnabled()){
			String time = new Timestamp(System.currentTimeMillis()).toString();
			LOG.debug("Publishing asynchronous message at: "+time+ " to topic \""+topicName+"\" qos "+qos);
		}

		// Construct the message to send
		MqttMessage message = new MqttMessage(payload);
		message.setQos(qos);

		try {
			IMqttDeliveryToken pubToken = client.publish(topicName, message, null, null);
		} catch (MqttException e) {
			throw new RuntimeException("problem asynchronously publishing message",e);
		} 	
	}

	/**
	 * subscribe to topic - this is not persistent across disconnections
	 * @param topic
	 * @param qos
	 */
	public void subscribe(String topic, int qos){
		try {
			client.subscribe(topic,qos);
		} catch (MqttException e) {
			throw new RuntimeException("problem subscribing to topic:"+topic+ " qos "+qos,e);
		}
	}

	/** 
	 * destroy method
	 */
	public void destroy(){
		try {
			clientConnected.set(false);
			stopConnectionRetryThead();
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			LOG.error("problem closing client",e);
		}
	}

	/**
	 * init method
	 */
	public void init(){
		if(m_useMqtt.get()) {
			LOG.info("useMqtt = true trying to connect to MQTT broker");
			startConnectionRetryThead();
		}else {
			LOG.info("useMqtt = false not using MQTT broker");
		}
	}


	/**
	 * Disconnect the client
	 * Issue the disconnect and then use a token to wait until
	 * the disconnect completes.
	 */
	public synchronized void disconnect(){
		LOG.debug("Disconnecting from MQTT broker");
		if(clientConnected.getAndSet(false)){
			try {
				IMqttToken discToken = client.disconnect(null, null);
				discToken.waitForCompletion();
			} catch (MqttException e1) {
				// An exception is thrown if connect fails.
				LOG.error("error when disconnecting from MQTT broker:",e1);
			}
		}
		LOG.debug("Disconnected from MQTT broker");
	}

	/**
	 * Starts trying to reconnect to the broker in a separate thread. 
	 * the retry interval sets how long between connection attempts the client waits
	 */
	private synchronized void startConnectionRetryThead(){
		if (m_connectionRetryThread==null){

			if(connectionRetryInterval==null) throw new RuntimeException("connectionretryInterval cannot be null");

			m_connectionRetryThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while (!Thread.currentThread().isInterrupted()) {

							LOG.debug("trying to connect to Mqtt broker");
							boolean success = false;
							try{
								success = connect();
							} catch(Exception e){
								LOG.error("exception thrown when trying to start MQTT connection.",e);
								throw new InterruptedException();
							}
							if(success) {
								// if connected then try to subscribe to topics. Try all topics and log failures
								LOG.debug("connected to Mqtt broker. Trying to subscribe to pre-set topics "+topicList.size());
								for(MQTTTopicSubscription subscription:topicList){
									try{
										LOG.debug(" subscribing to topic:"+subscription.getTopic()+" qos:"+subscription.getQos());
										subscribe(subscription.getTopic(), Integer.parseInt(subscription.getQos()));
									} catch(Exception e){
										LOG.error("exception thrown when trying to subscribe to topic.",e);
									}
								}

								throw new InterruptedException();
							}
							LOG.debug("waiting "+connectionRetryInterval
									+ "ms before retrying to connect to Mqtt broker");
							Thread.sleep(connectionRetryInterval);
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					LOG.debug("connection retry thread complete.");
				}
			});

			m_connectionRetryThread.start();
			LOG.info("connection retry thread started: retryInterval="+connectionRetryInterval);
		}
	}

	private synchronized void stopConnectionRetryThead(){
		clientConnected.set(false);
		if (m_connectionRetryThread!=null){
			m_connectionRetryThread.interrupt();
			m_connectionRetryThread=null;
			LOG.info("connection retry thread stopped");
		}
		disconnect();
	}


	/****************************************************************/
	/* Methods to implement the MqttCallback interface              */
	/****************************************************************/

	/**
	 * Called when the connection to the server has been lost. 
	 * An application may choose to implement reconnection
	 * logic at this point.
	 */
	@Override
	public void connectionLost(Throwable cause) {
		clientConnected.set(false);
		LOG.debug("Connection to " + brokerUrl + " lost!" + cause);
		startConnectionRetryThead();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// not used

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws MqttException {
		// Called when a message arrives from the server that matches any
		// subscription made by the client
		if(LOG.isDebugEnabled()){
			String time = new Timestamp(System.currentTimeMillis()).toString();
			LOG.debug("Time:\t" +time +
					"  Topic:\t" + topic +
					"  Message:\t" + new String(message.getPayload()) +
					"  QoS:\t" + message.getQos());
		}

		byte[] payload = message.getPayload();
		int qos = message.getQos();

		MessageNotification dbn = new MessageNotification(topic, qos, payload);

		// send notifications to registered clients - note each client must return quickly
		synchronized(messageNotificationClientList) {
			Iterator<MessageNotificationClient> i = messageNotificationClientList.iterator(); // Must be in synchronized block
			while (i.hasNext()){
				try{
					i.next().sendMessageNotification(dbn);
				} catch (Exception e){
					LOG.error("Problem actioning message notification.",e);
				}
			}         
		}

	}



	/****************************************************************/
	/* End of Methods to implement the MqttCallback interface              */
	/****************************************************************/


}
