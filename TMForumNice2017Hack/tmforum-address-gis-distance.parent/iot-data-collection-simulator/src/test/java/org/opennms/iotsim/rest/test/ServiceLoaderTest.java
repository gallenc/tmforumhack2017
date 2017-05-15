package org.opennms.iotsim.rest.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.model.IotDeviceType;
import org.opennms.iotsim.rest.ServiceLoader;

public class ServiceLoaderTest {

	@Test
	public void testPoperties(){
		System.out.println("Properties: "+ServiceLoader.getProperties());
		
	}
	
	@Test
	public void testDAO(){
		
		IotDeviceDAO dao = ServiceLoader.getIotDeviceDao();
		assertNotNull(dao.getDevices(IotDeviceType.FIXED));
		
	}
}
