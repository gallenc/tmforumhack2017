package org.opennms.tmforum.address.client.manual;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.gis.rest.DistanceMessage;
import org.opennms.tmforum.address.gis.rest.NearestAddressFinder;

public class NearestAddressFinderTest {

	// change to match server address you are testing against
	private static final  String TMFORUM_ADDRESS_URI="http://139.162.227.142:8080/addressManagement/api/addressManagement/v1";

	private NearestAddressFinder getNearestAddressFinder(){
		TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
		NearestAddressFinder nearestAddressFinder = new NearestAddressFinder(addressClient);
		return nearestAddressFinder;
	}

	@Test
	public void findNearestAddressTest() {
		System.out.println("Start of findNearestAddressTest()");
		NearestAddressFinder nearestAddressFinder=getNearestAddressFinder();

		Map<String, String> queryParams=null;

		// { "latitude" : "50.883794", "longitude" : "-1.393093" } },
		String latitude="50.883793";
		String longitude="-1.393093";

		DistanceMessage foundDistance = nearestAddressFinder.findNearestAddress(latitude, longitude, queryParams);

		if(foundDistance==null) {
			System.out.println("no address found");
		} else {
			System.out.println(foundDistance);
		}
		System.out.println("End of findNearestAddressTest()");
	}

	@Test
	public void findClosestAddressesTest() {
		System.out.println("Start of findClosestAddressesTest()");
		NearestAddressFinder nearestAddressFinder=getNearestAddressFinder();

		Map<String, String> queryParams=null;

		// { "latitude" : "50.883794", "longitude" : "-1.393093" } },
		String latitude="50.883794";
		String longitude="-1.393093";
		Integer maxReturnAddresses=null;

		Set<DistanceMessage> foundDistances = nearestAddressFinder.findClosestAddresses(latitude, longitude, maxReturnAddresses, queryParams);

		System.out.println("addresses found: "+foundDistances.size());
		for (DistanceMessage foundDistance: foundDistances){
			System.out.println(foundDistance);
		}
		System.out.println("End of findClosestAddressesTest()");
	}

}





