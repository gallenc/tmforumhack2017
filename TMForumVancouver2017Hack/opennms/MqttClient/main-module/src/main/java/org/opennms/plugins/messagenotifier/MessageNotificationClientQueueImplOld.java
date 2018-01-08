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

package org.opennms.plugins.messagenotifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * This is a notification client which receives the notification and adds it to a m_queue for processing
 * @author admin
 *
 */
public class MessageNotificationClientQueueImplOld implements MessageNotificationClient{

	private static final Logger LOG = LoggerFactory.getLogger(MessageNotificationClientQueueImplOld.class);

	//private MessageNotifier m_messageNotifier;

	private Integer m_maxMessageQueueLength=1000;
	
	private Integer maxMessageQueueThreads=1;

	private LinkedBlockingQueue<MessageNotification> m_queue=null;
	
	private AtomicBoolean m_clientRunning = new AtomicBoolean(false);

	private RemovingConsumer m_removingConsumer = new RemovingConsumer();
	
	private Thread m_removingConsumerThread = new Thread(m_removingConsumer);

	private Map<String,NotificationClient> m_topicHandlingClients = new HashMap<String, NotificationClient>();
	
    private List<MessageNotifier> m_messageNotifiers = null;

    @Override
	public List<MessageNotifier> getMessageNotifiers() {
		return m_messageNotifiers;
	}
    
    @Override
	public void setMessageNotifiers(List<MessageNotifier> messageNotifiers) {
		this.m_messageNotifiers = messageNotifiers;
	}

	/**
	 * @param m_topicHandlingClients the m_topicHandlingClients to set
	 */
	public void setTopicHandlingClients(Map<String,NotificationClient> topicHandlingClients) {
		this.m_topicHandlingClients.putAll(topicHandlingClients);
		if(LOG.isDebugEnabled()){
			StringBuffer sb = new StringBuffer("registering clients for topics: " );
			for(String topic: this.m_topicHandlingClients.keySet()){
				sb.append("topic:"+topic+" client:"+topicHandlingClients.get(topic)+", ");
			}
			LOG.debug(sb.toString());
		}
	}


	public Integer getMaxMessageQueueLength() {
		return m_maxMessageQueueLength;
	}

	public void setMaxMessageQueueLength(Integer maxMessageQueueLength) {
		this.m_maxMessageQueueLength = maxMessageQueueLength;
	}

	public Integer getMaxMessageQueueThreads() {
		return maxMessageQueueThreads;
	}

	public void setMaxMessageQueueThreads(Integer maxMessageQueueThreads) {
		this.maxMessageQueueThreads = maxMessageQueueThreads;
	}

	public void init(){
		LOG.debug("initialising messageNotificationClientQueue with m_queue size "+m_maxMessageQueueLength);
		
		if (m_messageNotifiers==null) throw new IllegalStateException("m_messageNotifiers list cannot be null");

		m_queue= new LinkedBlockingQueue<MessageNotification>(m_maxMessageQueueLength);

		// start consuming thread
		m_clientRunning.set(true);
		m_removingConsumerThread.start();

		// start listening for notifications
		for(MessageNotifier messageNotifier: m_messageNotifiers){
			messageNotifier.addMessageNotificationClient(this);
		}
		

	}

	public void destroy(){
		LOG.debug("shutting down client");

		// stop listening for notifications
		for(MessageNotifier messageNotifier: m_messageNotifiers){
			messageNotifier.removeMessageNotificationClient(this);
		}

		// signal consuming thread to stop
		m_clientRunning.set(false);
		m_removingConsumerThread.interrupt();
	}

	@Override
	public void sendMessageNotification(MessageNotification messageNotification) {
		if(LOG.isDebugEnabled()) LOG.debug("client received notification - adding notification to m_queue");

		if (! m_queue.offer(messageNotification)){
			LOG.warn("Cannot m_queue any more messageNotifications. messageNotification m_queue full. size="+m_queue.size());
		};

	}


	/*
	 * Class run in separate thread to remove and process notifications from the m_queue 
	 */
	private class RemovingConsumer implements Runnable {

		@Override
		public void run() {

			// we remove elements from the m_queue until interrupted and m_clientRunning==false.
			while (m_clientRunning.get()) {
				try {
					MessageNotification messageNotification = m_queue.take();

					if(LOG.isDebugEnabled()) LOG.debug("Notification received from m_queue by consumer thread :\n topic:"+messageNotification.getTopic()
							+ "\n qos:"+messageNotification.getQos()
							+ "\n payload:"+new String(messageNotification.getPayload()));

					// we look in hashtable for topic handling clients to handle this received notification
					if(m_topicHandlingClients.isEmpty()) { 
						LOG.warn("no topic handing clients have been set to receive notification");
					} else {
						NotificationClient topicHandlingClient = m_topicHandlingClients.get(messageNotification.getTopic());
						if (topicHandlingClient==null){
							LOG.warn("no topic handing client has been set for topic:"+messageNotification.getTopic());
						} else
							try {
								topicHandlingClient.sendMessageNotification(messageNotification);
							} catch (Exception e){
								LOG.error("problem processing messageNotification:",e);
							}
					}

				} catch (InterruptedException e) { }

			}

			LOG.debug("shutting down notification consumer thread");
		}
	}





}




