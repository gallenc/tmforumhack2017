package org.opennms.tmforum.address.gis.rest;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opennms.tmforum.address.client.TmforumAddressClient;

public class ServiceLoader {

	private static final String PROPERTIES_FILE_LOCATION_PROPERTY="tmforum-address-gis-distance.properties.file";

	private static final String DEFAULT_PROPERTIES_FILE_PROPERTY="tmforum-address-gis-distance.properties";

	// change to match server address you are testing against
	public static final  String DEFAULT_TMFORUM_ADDRESS_URI="/addressManagement/api/addressManagement/v1";
	public static final String TMFORUM_ADDRESS_URI_PROPERTY_NAME="tmforum.address.uri";

	public static final String TMFORUM_ADDRESS_SERVER_PROPERTY_NAME="tmforum.address.server";
	public static final String DEFAULT_TMFORUM_ADDRESS_SERVER="http://localhost:8080";

	// http://139.162.227.142:8080/addressManagement/api/addressManagement/v1
	//tmforum.address.uri = /addressManagement/api/addressManagement/v1
	//tmforum.address.server = http://localhost:8080

	private static NearestAddressFinderCache nearestAddressFinder=null;

	private static AddressCache addressCache;

	private static Properties systemProps=null;

	private static Object sync1 = new Object();

	public static Properties getProperties(){
		if(systemProps==null) synchronized (sync1) {
			if(systemProps==null) try {

				systemProps = System.getProperties();
				String propertiesFileLocation=systemProps.getProperty(PROPERTIES_FILE_LOCATION_PROPERTY);
				if(propertiesFileLocation==null){
					Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.INFO, "system property :"+PROPERTIES_FILE_LOCATION_PROPERTY+ 
							" not set. using default settings");
					propertiesFileLocation=DEFAULT_PROPERTIES_FILE_PROPERTY;
				}
				Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.INFO, "loading properties from:"+propertiesFileLocation);
				InputStream stream = ServiceLoader.class.getClassLoader().getResourceAsStream(propertiesFileLocation);
				if(stream!=null) {
					systemProps.load(stream);
				} else {
					Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.INFO, "not found:"+propertiesFileLocation);
				}
				Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.INFO, "systemProperties:"+systemProps.toString());
			} catch (Exception e){
				Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.SEVERE, "problem loading properties");
			}
		}
		return systemProps;
	}

	public static AddressCache getAddressCache() {
		if(addressCache==null) synchronized (sync1){
			if(addressCache==null) {
				String tmforumServerUri = getProperties().getProperty(TMFORUM_ADDRESS_SERVER_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_SERVER);
				String tmforumAddressUri = getProperties().getProperty(TMFORUM_ADDRESS_URI_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_URI);
				String tmforumAddressAPI= tmforumServerUri + tmforumAddressUri;
				Logger.getLogger(NearestAddressFinderCache.class.getName()).log(Level.INFO, "using tmforum address api at :"+tmforumAddressAPI);

				TmforumAddressClient addressClient = new TmforumAddressClient(tmforumAddressAPI);

				addressCache=new AddressCache(addressClient);
			}
		}
		return addressCache;
	}

	public static NearestAddressFinderCache getNearestAddressFinderCache(){
		if (nearestAddressFinder==null) synchronized (sync1){
			if (nearestAddressFinder==null) {
				nearestAddressFinder = new NearestAddressFinderCache(getAddressCache());
			}

		}
		return nearestAddressFinder;
	}


}
