package org.opennms.tmforum.address.gis.client.manual;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.gis.rest.AddressCache;
import org.opennms.tmforum.address.gis.rest.NearestAddressFinderCache;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;

public class NearestAddressFinderTest {
	
	// change to match server address you are testing against or put in properties file
	public static final String DEFAULT_TMFORUM_ADDRESS_URI="/addressManagement/api/addressManagement/v1";
	public static final String TMFORUM_ADDRESS_URI_PROPERTY_NAME="tmforum.address.uri";

	public static final String TMFORUM_ADDRESS_SERVER_PROPERTY_NAME="tmforum.address.server";
	public static final String DEFAULT_TMFORUM_ADDRESS_SERVER="http://localhost:8080";
	
	private static Properties testProperties = null;
	
	private static Properties getTestProperties(){
		if (testProperties==null){
			Properties systemProps = System.getProperties();
			InputStream stream = NearestAddressFinderTest.class.getClassLoader().getResourceAsStream("test.properties");
			try {
				if(stream!=null) {
					systemProps.load(stream);
				} else System.out.println("cannot load test properties file. Using defaults." );
                testProperties=systemProps;
			} catch (Exception e) {
				System.out.println("cannot load test properties file "+e);
			}
		}
		return testProperties;
	}
	
	private static String tmforumServerUri = getTestProperties().getProperty(TMFORUM_ADDRESS_SERVER_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_SERVER);
	private static String tmforumAddressUri = getTestProperties().getProperty(TMFORUM_ADDRESS_URI_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_URI);
	private static String tmforumAddressAPI= tmforumServerUri + tmforumAddressUri;

	private static NearestAddressFinderCache nearestAddressFinder=null;

	private static NearestAddressFinderCache getNearestAddressFinder(){
		if (nearestAddressFinder==null) {
			System.out.println("using address: "+tmforumAddressAPI);
			TmforumAddressClient addressClient = new TmforumAddressClient( tmforumAddressAPI);
			AddressCache addressCache = new AddressCache(addressClient);
			nearestAddressFinder = new NearestAddressFinderCache(addressCache);
		}
		return nearestAddressFinder;
	}

	@Test
	public void findNearestAddressTest() {
		System.out.println("Start of findNearestAddressTest()");
		findNearestAddress();
		System.out.println("End of findNearestAddressTest()");
	}
	
	private void findNearestAddress(){
		NearestAddressFinderCache nearestAddressFinder=getNearestAddressFinder();

		// "city" : "Southampton"
		MultivaluedMap<String, String> queryParams=new  MultivaluedHashMap<String, String>();
		queryParams.put("city", Arrays.asList("Southampton"));

		// { "latitude" : "50.883794", "longitude" : "-1.393093" } },
		String latitude="50.883793";
		String longitude="-1.393093";

		DistanceMessage foundDistance = nearestAddressFinder.findNearestAddress(latitude, longitude, queryParams);

		if(foundDistance==null) {
			System.out.println("no address found");
		} else {
			System.out.println("foundDistance:"+foundDistance+"  foundDistance.getAddress_finish().getId():"+foundDistance.getAddress_finish().getId()
					+" streetName:"+foundDistance.getAddress_finish().getStreetName());
		}
	}

	@Test
	public void findClosestAddressesTest() {
		System.out.println("Start of findClosestAddressesTest()");
		NearestAddressFinderCache nearestAddressFinder=getNearestAddressFinder();

		// "city" : "Southampton"
		MultivaluedMap<String, String> queryParams=new  MultivaluedHashMap<String, String>();
		queryParams.put("city", Arrays.asList("Southampton"));
		queryParams.put("streetName", Arrays.asList("Itchen Quays")); // narrow returned list

		// { "latitude" : "50.883794", "longitude" : "-1.393093" } },
		String latitude="50.883794";
		String longitude="-1.393093";
		Integer maxReturnAddresses=null;

		Set<DistanceMessage> foundDistances = nearestAddressFinder.findClosestAddresses(latitude, longitude, maxReturnAddresses, queryParams);

		System.out.println("addresses found: "+foundDistances.size());
		for (DistanceMessage foundDistance: foundDistances){
			System.out.println("foundDistance:"+foundDistance+"  foundDistance.getAddress_finish().getId():"+foundDistance.getAddress_finish().getId()
					+" streetName:"+foundDistance.getAddress_finish().getStreetName());
		}
		System.out.println("End of findClosestAddressesTest()");
	}

	@Test
	public void testCache(){
		Date date=new Date();
		System.out.println("***** Start of testCache(). Start time = " +date.getTime());
		for (int i=0; i<50; i++){
			findNearestAddress();
		}
		Date enddate=new Date();
		System.out.println("***** End of testCache().  end time = " +enddate.getTime()
				+ " duration = " + (enddate.getTime()-date.getTime()) );
	}

}





