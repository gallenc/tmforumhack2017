package org.opennms.plugins.sniffysimulator;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.opennms.plugins.mqttclient.MQTTClientImpl;
import org.opennms.plugins.sniffysimulator.jaxb.SniffyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SniffyController implements SniffyService {
	private static final Logger LOG = LoggerFactory.getLogger(SniffyController.class);

	private final Object lock = new Object();
	
	private SniffyData sdata=null;
	
	private Thread m_timer=null;
	
	private SniffyDataCollector sniffyDataCollector=null;
	
	private MQTTClientImpl mqttClient=null;
	
	private String id=null;
	private String cityName=null;
	private String stationName=null;
	private String latitude=null;
	private String longitude=null;
	private String averaging=null;
	private String topicName;
	private Integer collectionInterval=10000; // collection inteval ms


	private int qos;
	
	public SniffyDataCollector getSniffyDataCollector() {
		return sniffyDataCollector;
	}

	public void setSniffyDataCollector(SniffyDataCollector sniffyDataCollector) {
		this.sniffyDataCollector = sniffyDataCollector;
	}

	public MQTTClientImpl getMqttClient() {
		return mqttClient;
	}

	public void setMqttClient(MQTTClientImpl mqttClient) {
		this.mqttClient = mqttClient;
	}

	public String getCollectionInterval() {
		return Integer.toString(collectionInterval);
	}

	public void setCollectionInterval(String collectionInterval) {
		this.collectionInterval = Integer.parseInt(collectionInterval);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAveraging() {
		return averaging;
	}

	public void setAveraging(String averaging) {
		this.averaging = averaging;
	}
	
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}
	

	@Override
	public SniffyData getSniffyData() {
        synchronized (lock) {
            return sdata;
        }
	}

	private SniffyData getMeasurement() {
		SniffyData measurement = sniffyDataCollector.getMeasurement();
		measurement.setId(id);
		measurement.setCityName(cityName);
		measurement.setStationName(stationName);
		measurement.setAveraging(averaging);
		measurement.setLatitude(latitude);
		measurement.setLongitude(longitude);
        synchronized (lock) {
            sdata = measurement;
        }
        LOG.debug("Measurement collected:"+measurement.toString());
        return measurement;
	}

	/**
	 * init method
	 */
	public void init(){
		LOG.debug("sniffy starting data collection thread");
		startDataCollectionThead();
	}
	
	public void destroy(){
		LOG.debug("sniffy stopping data collection thread");
		stopDataCollectionThead();
	}


	private synchronized void startDataCollectionThead(){
		if (m_timer==null){

			if(collectionInterval==null) throw new RuntimeException("collectionInterval cannot be null");

			m_timer = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						while (!Thread.currentThread().isInterrupted()) {
							LOG.debug("trying to collect measurement");
							SniffyData sniffyData = getMeasurement();
							
							String json = sniffyDataToMQTTJson(sniffyData);
							
							byte[] payload=null;
							try {
								payload = json.getBytes("UTF8");
								mqttClient.publishAsynchronous(topicName, qos, payload); 
							} catch (Exception e) {
								LOG.debug("problem publishing json="+json,e);
							}
							
							LOG.debug("waiting "+collectionInterval+ "ms before next collection");
							Thread.sleep(collectionInterval);
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					LOG.debug("collection thread ended.");
				}
			});

			m_timer.start();
			LOG.info("data collection thread started : retryInterval="+collectionInterval);
		}
	}

	private synchronized void stopDataCollectionThead(){
		if (m_timer!=null){
			m_timer.interrupt();
			m_timer=null;
			LOG.info("collection thread thread stopped");
		}
	}
	
	public static String sniffyDataToMQTTJson(SniffyData sniffyData){
		String result=null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
			result = mapper.writeValueAsString(sniffyData);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("problem converting sniffyData to json:",e);
		}
	}

}
