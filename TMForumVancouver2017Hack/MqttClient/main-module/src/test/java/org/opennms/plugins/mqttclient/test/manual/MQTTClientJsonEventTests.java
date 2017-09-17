package org.opennms.plugins.mqttclient.test.manual;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.opennms.plugins.messagenotifier.MessageNotification;
import org.opennms.plugins.messagenotifier.MessageNotificationClientQueueImpl;
import org.opennms.plugins.messagenotifier.NotificationClient;
import org.opennms.plugins.messagenotifier.VerySimpleMessageNotificationClient;
import org.opennms.plugins.messagenotifier.alarmnotifier.MqttEventNotificationClient;
import org.opennms.plugins.mqttclient.MQTTClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClientJsonEventTests {
	private static final Logger LOG = LoggerFactory.getLogger(MQTTClientJsonEventTests.class);

	public static final String SERVER_URL = "tcp://localhost:1883";

	public static final String CLIENT_ID = "receiver1";
	public static final String CLIENT_ID2 = "transmitter1";
	public static final String TOPIC_NAME = "mqtt-events";
	public static final int QOS_LEVEL = 0;

	public static final String jsonTestMessage="{"
			+ " \"time\": \""+ jsonTime(new Date())+ "\","
			+ " \"id\": \"monitorID\","
			+ " \"cityName\": \"Southampton\","
			+ " \"stationName\": \"Common#1\","
			+ " \"latitude\": 0,"
			+ " \"longitude\": 0,"
			+ " \"averaging\": 0,"
			+ " \"PM1\": 10,"
			+ " \"PM25\": 100,"
			+ " \"PM10\": 1000"
			+ "}";

	@Test
	public void testJsonMessage(){
		LOG.debug("start testJsonMessage()");
		JSONObject jsonobj;
		try {
			jsonobj = parseJson(jsonTestMessage);
			LOG.debug("testJsonMessage() JSON object:"+jsonobj.toJSONString() );
		} catch (ParseException e) {
			LOG.debug("failed to parse message:"+jsonTestMessage,e);		
			fail("failed to parse message:"+jsonTestMessage);
		}
		LOG.debug("end testJsonMessage()");
	}

	public static String jsonTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );

		TimeZone tz = TimeZone.getTimeZone( "UTC" );

		df.setTimeZone( tz );

		String output = df.format( date );
		return output;
	}

	@Test
	public void testTopicConnection() {
		LOG.debug("start of test testTopicConnection() ");

		// set up receiver
		Receiver receiver= new Receiver();
		Thread thread = new Thread(receiver);
		thread.start();

		try {
			Thread.sleep(5000);
			assertTrue(receiver.isConnected());
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		LOG.debug("Receiver is connected");

		// set up transmitter
		String brokerUrl = SERVER_URL;
		String clientId = CLIENT_ID2;
		String userName =null;
		String password =null;
		String connectionRetryInterval= "1000" ;

		MQTTClientImpl client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);

		// will connect
		brokerUrl = SERVER_URL;
		client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);

		try{
			client.init();
		} catch(Exception e){
			LOG.debug("problem initialising reliable transmitter connection", e);
		}

		// wait for connection
		try {
			Thread.sleep(3000);
			assertTrue(client.isClientConnected());
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		// try sending messages
		String topic=TOPIC_NAME;
		int qos=QOS_LEVEL;

		// send text message
		try{
			String message="text message not json";
			byte[] payload = message.getBytes();
			client.publishSynchronous(topic, qos, payload);
		} catch(Exception e){
			LOG.debug("problem publishing message", e);
		}

		// send json message
		try{
			JSONObject jsonobj = parseJson(jsonTestMessage);
			String message=jsonobj.toJSONString();
			byte[] payload = message.getBytes();
			client.publishSynchronous(topic, qos, payload);
		} catch(Exception e){
			LOG.debug("problem publishing json message", e);
		}

		// wait for received messages
		try {
			Thread.sleep(5000);

		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		//clean up
		client.destroy();
		receiver.close();

		LOG.debug("end of test testTopicConnection() ");
	}


	private JSONObject parseJson(String payloadString) throws ParseException{
		JSONObject jsonObject=null;

		LOG.debug("parseJson payloadString:" + payloadString);
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(payloadString);
		jsonObject = (JSONObject) obj;
		LOG.debug("parseJson JsonObject.toString():" + jsonObject.toString());

		return jsonObject;
	}


	private class Receiver implements Runnable {

		MQTTClientImpl client;
		MessageNotificationClientQueueImpl messageNotificationClientQueueImpl;

		public boolean isConnected(){
			if(client!=null) return client.isClientConnected();
			return false;
		}

		public void close(){
			LOG.debug("Receiver closing connection");
			if (client!=null) client.destroy();
			client=null;
			if (messageNotificationClientQueueImpl!=null ) messageNotificationClientQueueImpl.destroy();
		}

		@Override
		public void run() {
			String brokerUrl = SERVER_URL;
			String clientId = CLIENT_ID;
			String userName =null;
			String password =null;
			String connectionRetryInterval= "1000" ;

			LOG.debug("Receiver initiating connection");

			client = new MQTTClientImpl(brokerUrl, clientId, userName, password, connectionRetryInterval);

			messageNotificationClientQueueImpl = new MessageNotificationClientQueueImpl();

			messageNotificationClientQueueImpl.setMessageNotifier(client);

			messageNotificationClientQueueImpl.setMaxQueueLength(100);

			Map<String, NotificationClient> topicHandlingClients = new HashMap<String, NotificationClient>();
			NotificationClient notificationClient = new MqttEventNotificationClient();

			topicHandlingClients.put(TOPIC_NAME, notificationClient);
			messageNotificationClientQueueImpl.setTopicHandlingClients(topicHandlingClients);

			try{
				messageNotificationClientQueueImpl.init();
				client.init();
			} catch(Exception e){
				LOG.debug("Receiver problem initialising reliable connection", e);
			}

			// wait for receiver to connect
			try {
				while (!Thread.currentThread().isInterrupted() && ! client.isClientConnected()){
					Thread.sleep(100);
				}
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			// try sending message
			String topic=TOPIC_NAME;
			int qos=QOS_LEVEL;
			try{
				client.subscribe(topic, qos);
			} catch(Exception e){
				LOG.debug("Receiver problem subscribing", e);
			}
			LOG.debug("Receiver connection initialised");
		}

	}

}
