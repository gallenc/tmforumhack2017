package org.opennms.tmforum.address.gis.client.manual;

import static org.junit.Assert.*;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

public class TempTest {

	@Test
	public void test() {
		MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<String, String>();
		
		queryParams.add("key0ne", "1value1");
		queryParams.add("key0ne", "1value2");
		queryParams.add("keyTwo", "2value1");
		queryParams.add("keyTwo", "2value2");
		queryParams.add("keyTwe", "3value1");
		queryParams.add("keyTwe", "3value2");
		
		
		System.out.println("one "+queryParams);
		
		queryParams = new MultivaluedHashMap<String, String>(queryParams);
		
		System.out.println("two "+queryParams);
	}

}
