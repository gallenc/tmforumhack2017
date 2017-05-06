package org.opennms.tmforum.address.client.manual;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import org.junit.Test;
import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.model.Address;

public class AddressClientTest {
	
	// change to match server address you are testing against
	private static final  String TMFORUM_ADDRESS_URI="http://139.162.227.142:8080/addressManagement/api/addressManagement/v1";

	@Test
	public void getAddressestest1() {
		System.out.println("START OF getAddressestest1()");
		
		TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);

		Map<String, String> queryMap=null;
		Set<Address> addressList= addressClient.getAddresses(queryMap);

		for(Address address:addressList){
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}
		System.out.println("END OF getAddressestest1()");
	}
	
	@Test
	public void getAddressestest2() {
		System.out.println("START OF getAddressestest2()");
		// GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetName=Itchen Quays
		
		TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
		
		
		Map<String, String> queryMap=new LinkedHashMap<String, String>();
		queryMap.put("streetName", "Itchen Quays");
		Set<Address> addressList= addressClient.getAddresses(queryMap);

		for(Address address:addressList){
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}
		System.out.println("END OF getAddressestest2()");
	}

	
	@Test
	public void getAddressestest3() {
		System.out.println("START OF getAddressestest3()");
		// GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetNr=30
		
		TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
		
		
		Map<String, String> queryMap=new LinkedHashMap<String, String>();
		queryMap.put("streetNr", "30");
		Set<Address> addressList= addressClient.getAddresses(queryMap);

		for(Address address:addressList){
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}
		System.out.println("END OF getAddressestest3()");

	}
	
	@Test
	public void getAddressTest() {
		System.out.println("START OF getAddressTest()");
		// GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address/214
		
		TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
		
		String id = "214"; // CHANGE TO MATCH A REAL ADDRESS
		Address address= addressClient.getAddress(id );
		if (address==null) {
			System.out.println("could not retreive address id="+id);
		} else {
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}

		System.out.println("END OF getAddressTest()");

	}



}
