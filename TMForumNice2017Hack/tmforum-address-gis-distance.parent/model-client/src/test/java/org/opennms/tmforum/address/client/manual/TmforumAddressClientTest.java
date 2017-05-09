package org.opennms.tmforum.address.client.manual;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.model.Address;

public class TmforumAddressClientTest {
	
	// change to match server address you are testing against or put in properties file
	public static final String DEFAULT_TMFORUM_ADDRESS_URI="/addressManagement/api/addressManagement/v1";
	public static final String TMFORUM_ADDRESS_URI_PROPERTY_NAME="tmforum.address.uri";

	public static final String TMFORUM_ADDRESS_SERVER_PROPERTY_NAME="tmforum.address.server";
	public static final String DEFAULT_TMFORUM_ADDRESS_SERVER="http://localhost:8080";
	
	private static Properties testProperties = null;
	
	private static Properties getTestProperties(){
		if (testProperties==null){
			Properties systemProps = System.getProperties();
			InputStream stream = TmforumAddressClientTest.class.getClassLoader().getResourceAsStream("test.properties");
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
	
	private String tmforumServerUri = getTestProperties().getProperty(TMFORUM_ADDRESS_SERVER_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_SERVER);
	private String tmforumAddressUri = getTestProperties().getProperty(TMFORUM_ADDRESS_URI_PROPERTY_NAME, DEFAULT_TMFORUM_ADDRESS_URI);
	private String tmforumAddressAPI= tmforumServerUri + tmforumAddressUri;

	// get all addresses
	@Test
	public void getAddressestest1() {
		System.out.println("START OF getAddressestest1()");
		
		System.out.println("using address: "+tmforumAddressAPI);
		TmforumAddressClient addressClient = new TmforumAddressClient(tmforumAddressAPI);

		MultivaluedMap<String, String> queryMap=null;
		Set<Address> addressList= addressClient.getAddresses(queryMap);

		for(Address address:addressList){
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}
		System.out.println("END OF getAddressestest1()");
	}
	
	// get addresses near "Itchen Quays"
	@Test
	public void getAddressestest2() {
		System.out.println("START OF getAddressestest2()");
		// GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetName=Itchen Quays
		
		System.out.println("using address: "+tmforumAddressAPI);
		TmforumAddressClient addressClient = new TmforumAddressClient(tmforumAddressAPI);
		
        MultivaluedMap<String, String> queryMap=new  MultivaluedHashMap<String, String>();
        
        queryMap.put("streetName", Arrays.asList("Itchen Quays"));

		Set<Address> addressList= addressClient.getAddresses(queryMap);

		for(Address address:addressList){
			System.out.println("address id="+address.getId() +" address streetName="+address.getStreetName() +" address streetNr="+address.getStreetNr());
		}
		System.out.println("END OF getAddressestest2()");
	}

	// get street numbers 30 31
	@Test
	public void getAddressestest3() {
		System.out.println("START OF getAddressestest3()");
		// GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetNr=30
		// http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetNr=30&streetNr=31
		
		System.out.println("using address: "+tmforumAddressAPI);
		TmforumAddressClient addressClient = new TmforumAddressClient(tmforumAddressAPI);
		
		MultivaluedMap<String, String> queryMap=new MultivaluedHashMap<String, String>();
		List<String> list = Arrays.asList("30","31");
		queryMap.put("streetNr",list);
		
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
		
		System.out.println("using address: "+tmforumAddressAPI);
		TmforumAddressClient addressClient = new TmforumAddressClient(tmforumAddressAPI);
		
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
