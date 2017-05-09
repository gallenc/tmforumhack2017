package org.opennms.tmforum.address.client.manual;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.gis.rest.NearestAddressFinder;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;

public class NearestAddressFinderTest {

	// change to match server address you are testing against
	private static final  String TMFORUM_ADDRESS_URI="http://139.162.227.142:8080/addressManagement/api/addressManagement/v1";

	private static NearestAddressFinder nearestAddressFinder=null;

	private static NearestAddressFinder getNearestAddressFinder(){
		if (nearestAddressFinder==null) {
			TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
			nearestAddressFinder = new NearestAddressFinder(addressClient);
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
		NearestAddressFinder nearestAddressFinder=getNearestAddressFinder();

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
		NearestAddressFinder nearestAddressFinder=getNearestAddressFinder();

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





