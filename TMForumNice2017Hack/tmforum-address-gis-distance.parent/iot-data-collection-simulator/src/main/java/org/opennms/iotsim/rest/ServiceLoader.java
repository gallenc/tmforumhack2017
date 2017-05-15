package org.opennms.iotsim.rest;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opennms.iotsim.model.IotDeviceDAO;


public class ServiceLoader {

	private static final String PROPERTIES_FILE_LOCATION_PROPERTY="tmforum-address-gis-distance.properties.file";

	private static final String DEFAULT_PROPERTIES_FILE_PROPERTY="tmforum-address-gis-distance.properties";

	// change to match server address you are testing against
	public static final  String DEFAULT_TMFORUM_ADDRESS_URI="/addressManagement/api/addressManagement/v1";
	public static final String TMFORUM_ADDRESS_URI_PROPERTY_NAME="tmforum.address.uri";

	public static final String TMFORUM_ADDRESS_SERVER_PROPERTY_NAME="tmforum.address.server";
	public static final String DEFAULT_TMFORUM_ADDRESS_SERVER="http://localhost:8080";


	private static IotDeviceDAO iotDeviceDao = new IotDeviceDAO();
	
	private static Properties systemProps=null;
	
	
	public static IotDeviceDAO getIotDeviceDao(){
		return iotDeviceDao;
	}

	public static Properties getProperties(){
		if(systemProps==null) try{
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
		return systemProps;
	}




}
