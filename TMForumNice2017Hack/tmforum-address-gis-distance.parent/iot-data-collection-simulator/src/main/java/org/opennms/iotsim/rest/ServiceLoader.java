package org.opennms.iotsim.rest;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.simulator.FixedSensorSimulator;
import org.opennms.iotsim.simulator.MobileSensorSimulator;
import org.opennms.iotsim.simulator.EmissionSimulator;
import org.opennms.tmforum.address.model.Address;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ServiceLoader {

	private static final String PROPERTIES_FILE_LOCATION_PROPERTY="tmforum-address-gis-distance.properties.file";

	private static final String DEFAULT_PROPERTIES_FILE_PROPERTY="tmforum-address-gis-distance.properties";

	// change to match server address you are testing against
	public static final  String DEFAULT_TMFORUM_ADDRESS_URI="/addressManagement/api/addressManagement/v1";
	public static final String TMFORUM_ADDRESS_URI_PROPERTY_NAME="tmforum.address.uri";

	public static final String TMFORUM_ADDRESS_SERVER_PROPERTY_NAME="tmforum.address.server";
	public static final String DEFAULT_TMFORUM_ADDRESS_SERVER="http://localhost:8080";
	
	private static Object sync1 = new Object();

	private static FixedSensorSimulator fixedDeviceSimulator = null;
	
	private static MobileSensorSimulator mobileDeviceSimulator = null;
	
	private static EmissionSimulator simulationDeviceSimulator = null;
	
	private  static IotDeviceDAO iotDeviceDao = new IotDeviceDAO();
	
	private  static Properties systemProps=null;
	
	public static IotDeviceDAO getIotDeviceDao(){
		return iotDeviceDao;
	}

	public static FixedSensorSimulator getFixedDeviceSimulator() {
		if(fixedDeviceSimulator==null) synchronized (sync1) {
			if(fixedDeviceSimulator==null) try {
				fixedDeviceSimulator=new FixedSensorSimulator();
				fixedDeviceSimulator.setIotDeviceDao(iotDeviceDao);
			} catch (Exception e){
				Logger.getLogger(ServiceLoader.class.getName()).log(Level.SEVERE, "problem loading FixedDeviceSimulator");
			}
		}	
		return fixedDeviceSimulator;
	}

	public static EmissionSimulator getSimulationDeviceSimulator() {
		if(simulationDeviceSimulator==null) synchronized (sync1) {
			if(simulationDeviceSimulator==null) try {
				simulationDeviceSimulator=new EmissionSimulator();
				simulationDeviceSimulator.setIotDeviceDao(iotDeviceDao);
			} catch (Exception e){
				Logger.getLogger(ServiceLoader.class.getName()).log(Level.SEVERE, "problem SimulationDeviceSimulator");
			}
		}	
		return simulationDeviceSimulator;
	}



	public static MobileSensorSimulator getMobiledevicesimulator() {
		if(mobileDeviceSimulator==null) synchronized (sync1) {
			if(mobileDeviceSimulator==null) try {
				mobileDeviceSimulator=new MobileSensorSimulator();
				mobileDeviceSimulator.setIotDeviceDao(iotDeviceDao);
			} catch (Exception e){
				Logger.getLogger(ServiceLoader.class.getName()).log(Level.SEVERE, "problem MobileDeviceSimulator");
			}
		}
		return mobileDeviceSimulator;
	}
	
	public static Properties getSystemProps() {
		return systemProps;
	}
	
	public static Properties getProperties(){
		if(systemProps==null) synchronized (sync1) {
			if(systemProps==null) try {
			systemProps = System.getProperties();
			String propertiesFileLocation=systemProps.getProperty(PROPERTIES_FILE_LOCATION_PROPERTY);
			if(propertiesFileLocation==null){
				Logger.getLogger(ServiceLoader.class.getName()).log(Level.INFO, "system property :"+PROPERTIES_FILE_LOCATION_PROPERTY+ 
						" not set. using default settings");
				propertiesFileLocation=DEFAULT_PROPERTIES_FILE_PROPERTY;
			}
			Logger.getLogger(ServiceLoader.class.getName()).log(Level.INFO, "loading properties from:"+propertiesFileLocation);
			InputStream stream = ServiceLoader.class.getClassLoader().getResourceAsStream(propertiesFileLocation);
			if(stream!=null) {
				systemProps.load(stream);
			} else {
				Logger.getLogger(ServiceLoader.class.getName()).log(Level.INFO, "not found:"+propertiesFileLocation);
			}
			Logger.getLogger(ServiceLoader.class.getName()).log(Level.INFO, "systemProperties:"+systemProps.toString());
		} catch (Exception e){
			Logger.getLogger(ServiceLoader.class.getName()).log(Level.SEVERE, "problem loading properties");
		}
		}
		return systemProps;
	}

	
}
