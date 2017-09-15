package org.opennms.plugins.mqttclient.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.plugins.mqttclient.MQTTClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(MQTTClientTest.class);


	public static final String SERVER_URL = "tcp://localhost:1883";
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
		int connectionRetryInterval= 10000 ;

		MQTTClientImpl client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);
		
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
	
	//@Test
	public void testReliableConnection() {
		LOG.debug("start of test testSimpleConnection() ");

		String brokerUrl = SERVER_URL;
		String clientId = CLIENT_ID;
		String userName =null;
		String password =null;
		int connectionRetryInterval= 10000 ;

		MQTTClientImpl client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);
		
		try{
			client.startConnectionRetryThead();
			
			
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

}
