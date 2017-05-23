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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceType;
import org.opennms.iotsim.model.KeyValuePair;
import org.opennms.tmforum.address.model.GeoCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class IotDeviceTest {

public String writeJson(Object obj){
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	
	String iotStr=null;
	try {
		iotStr = objectMapper.writeValueAsString(obj);
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return iotStr;
}

	@Test
	public void test() {
		
		IotDevice iotDevice = new IotDevice(IotDeviceType.FIXED);
		
		String label="testIot1";
		iotDevice.setLabel(label);
		
		GeoCode geocode= new GeoCode();
		geocode.setLatitude("50.889311");
		geocode.setLongitude("-1.391915");
		
		List<GeoCode> mission= Arrays.asList(geocode);
		iotDevice.setMission(mission);
		KeyValuePair nvp = new KeyValuePair();
		nvp.setKey("key1");
		nvp.setValue("value1");
		List<KeyValuePair> parameters = Arrays.asList(nvp);
		iotDevice.setParameters(parameters);
		String speed= Integer.toString(100);
		iotDevice.setSpeed(speed);
				
		String iotStr=null;
		
		iotStr = writeJson(iotDevice);

		System.out.println(iotStr);

	}
	


}
