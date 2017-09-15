package org.opennms.plugins.mqttclient;

import java.sql.Timestamp;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientImpl implements MqttCallback {
	private static final Logger LOG = LoggerFactory.getLogger(MQTTClientImpl.class);
	
	private AtomicInteger reconnectionCount = new AtomicInteger(0);

	// Private instance variables
	private MqttAsyncClient 	client;

	boolean connected= false;

	private MqttConnectOptions 	conOpt;
	private boolean clean=true;

	private String brokerUrl;
	private String password;
	private String userName;

	private AtomicBoolean clientConnected = new AtomicBoolean(false); 

	private Thread m_connectionRetryThread=null;

	// connectionRetryInterval   interval (ms) before re attempting connection.
	private Integer connectionRetryInterval=30000;

	/**
	 * @param brokerUrl the url to connect to
	 * @param clientId the client id to connect with
	 * @param userName the username to connect with
	 * @param password the password for the user
	 * @param connectionRetryInterval   interval (ms) before re attempting connection.
	 */
	public MQTTClientImpl(String brokerUrl, String clientId, String userName, String password, int connectionRetryInterval) {
		this.brokerUrl = brokerUrl;
		this.userName = userName;
		this.password = password;
		this.connectionRetryInterval=connectionRetryInterval;

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
		LOG.debug("Connecting to "+brokerUrl + " with client ID "+client.getClientId());
		IMqttToken conToken;
		try {
			conToken = client.connect(conOpt,null,null);
			conToken.waitForCompletion();
		} catch (MqttException e1) {
			// An exception is thrown if connect fails.
			LOG.error("failed to connect to MQTT broker:",e1);
			clientConnected.set(false);
			return false;
		}
		LOG.debug("Connected to MQTT broker (number of connection attempts since start="
				+ reconnectionCount.incrementAndGet()+")");
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
			throw new RuntimeException("problem synchronously publishing message",e);
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
			LOG.debug("Publishing asynchronpus message at: "+time+ " to topic \""+topicName+"\" qos "+qos);
		}

		// Construct the message to send
		MqttMessage message = new MqttMessage(payload);
		message.setQos(qos);

		try {
			IMqttDeliveryToken pubToken = client.publish(topicName, message, null, null);
		} catch (MqttException e) {
			throw new RuntimeException("problem synchronously publishing message",e);
		} 	
	}

	public void subscribe(String topic, int qos){
		try {
			client.subscribe(topic,qos);
		} catch (MqttException e) {
			throw new RuntimeException("problem subscribing to topic:"+topic+ " qos "+qos,e);
		}
	}

	public void destroy(){
		try {
			clientConnected.set(false);
			stopConnectionRetryThead();
			client.close();
		} catch (MqttException e) {
			LOG.error("problem closing client");
		}
	}


	/**
	 * Disconnect the client
	 * Issue the disconnect and then use a token to wait until
	 * the disconnect completes.
	 */
	public synchronized void disconnect(){
		clientConnected.set(false);
		LOG.debug("Disconnecting from MQTT broker");
		try {
			IMqttToken discToken = client.disconnect(null, null);
			discToken.waitForCompletion();
		} catch (MqttException e1) {
			// An exception is thrown if connect fails.
			LOG.error("error when disconnecting from MQTT broker:",e1);
		}
		LOG.debug("Disconnected from MQTT broker");
	}

	/**
	 * Starts trying to reconnect to the broker in a separate thread. 
	 * the retry interval sets how long between connection attempts the client waits
	 */
	public synchronized void startConnectionRetryThead(){
		if (m_connectionRetryThread==null){
			
			if(connectionRetryInterval==null) throw new RuntimeException("retryInterval cannot be null");

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
							if(success) throw new InterruptedException();
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


	public synchronized void stopConnectionRetryThead(){
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
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" +time +
				"  Topic:\t" + topic +
				"  Message:\t" + new String(message.getPayload()) +
				"  QoS:\t" + message.getQos());

	}

	/****************************************************************/
	/* End of Methods to implement the MqttCallback interface              */
	/****************************************************************/


}
