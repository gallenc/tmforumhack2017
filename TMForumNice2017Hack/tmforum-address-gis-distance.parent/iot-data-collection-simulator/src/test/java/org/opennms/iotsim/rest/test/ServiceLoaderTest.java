/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
