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
