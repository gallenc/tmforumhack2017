package org.opennms.tmforum.address.gis;

import org.junit.Test;
import org.opennms.iotsim.rest.ServiceLoader;

public class ServiceLoaderTest {

	@Test
	public void test(){
		System.out.println("Properties: "+ServiceLoader.getProperties());
		
	}
}
