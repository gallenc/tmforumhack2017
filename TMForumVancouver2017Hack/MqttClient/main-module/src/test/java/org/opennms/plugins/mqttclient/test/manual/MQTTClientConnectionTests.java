package org.opennms.plugins.mqttclient.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.plugins.mqttclient.MQTTClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientConnectionTests {
	private static final Logger LOG = LoggerFactory.getLogger(MQTTClientConnectionTests.class);


	public static final String SERVER_URL = "tcp://localhost:1883";
	public static final String WRONG_SERVER_URL = "tcp://localhost:1884";
	
	public static final String CLIENT_ID = "sewatech";
	public static final String TOPIC_NAME = "sewatech";
	public static final int QOS_LEVEL = 0;


	@Test
	public void testSimpleConnection() {
		LOG.debug("start of test testSimpleConnection() ");

		String brokerUrl = SERVER_URL;
		String clientId = CLIENT_ID;
		String userName =null;
		String password =null;
		String connectionRetryInterval= "10000" ;


		MQTTClientImpl client = new MQTTClientImpl(brokerUrl, clientId, userName, password,connectionRetryInterval );

		try{
			boolean connected = client.connect();
			LOG.debug("client connected="+connected);
		} catch(Exception e){
			LOG.debug("problem connecting", e);
		}

		String topic=TOPIC_NAME;
		int qos=QOS_LEVEL;
		try{
			client.subscribe(topic, qos);
		} catch(Exception e){
			LOG.debug("problem subscribing", e);
		}

		byte[] payload = "Hello from testSimpleConnection()".getBytes();

		try{
			client.publishSynchronous(topic, qos, payload);
		} catch(Exception e){
			LOG.debug("problem publishing message", e);
		}

		LOG.debug("end of test testSimpleConnection() ");
	}

	@Test
	public void testReliableConnection() {
		LOG.debug("start of test testReliableConnection() ");

		String brokerUrl = WRONG_SERVER_URL;
		String clientId = CLIENT_ID;
		String userName =null;
		String password =null;
		String connectionRetryInterval= "1000" ;

		MQTTClientImpl client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);

		// wont connect
		LOG.debug("Testing connection retrys with bad url - should not connect ");
		try{
			client.init();
		} catch(Exception e){
			LOG.debug("problem initialising reliable connection", e);
		}

		try {
			Thread.sleep(3000);
			assertFalse(client.isClientConnected());
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
        client.destroy();
		
        LOG.debug("Testing connection retrys with good url - should connect first time ");
		// will connect
        brokerUrl = SERVER_URL;
		client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);

		try{
			client.init();
		} catch(Exception e){
			LOG.debug("problem initialising reliable connection", e);
		}

		try {
			Thread.sleep(3000);
			assertTrue(client.isClientConnected());
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		// try sending message
		String topic=TOPIC_NAME;
		int qos=QOS_LEVEL;
		try{
			client.subscribe(topic, qos);
		} catch(Exception e){
			LOG.debug("problem subscribing", e);
		}

		byte[] payload = "Hello from testReliableConnection()".getBytes();

		try{
			client.publishSynchronous(topic, qos, payload);
		} catch(Exception e){
			LOG.debug("problem publishing message", e);
		}

		LOG.debug("end of test testReliableConnection() ");
	}

}
