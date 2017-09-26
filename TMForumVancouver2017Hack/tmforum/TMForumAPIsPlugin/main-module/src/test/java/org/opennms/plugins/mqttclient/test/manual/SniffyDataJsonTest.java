package org.opennms.plugins.mqttclient.test.manual;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.junit.Test;
import org.opennms.plugins.sniffysimulator.jaxb.SniffyData;

public class SniffyDataJsonTest {

	@Test
	public void test() {
		
		SniffyData sd=new SniffyData();
		sd.setId("systemid");
		sd.setAveraging("0");
		sd.setCityName("cityName");
		sd.setLatitude("0.0");
		sd.setLongitude("57.0");
		sd.setPM1("0.001");
		sd.setPM10("0.001");
		sd.setPM25("0.001");
		sd.setStationName("stationName");
		
		Date d = new Date();
		sd.setTime(sd.jsonTime(d));

		ObjectMapper mapper = new ObjectMapper();
        
		mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
		         
		// Printing JSON
		String result;
		try {
			System.out.println("starting SniffyData:"+sd);
			result = mapper.writeValueAsString(sd);
			System.out.println("SniffyData to Json:"+result);
	         
			// Parsing JSON
			SniffyData retr = mapper.readValue(result, SniffyData.class);
			System.out.println("received SniffyData"+retr);
			
			assertEquals(sd.toString(),retr.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
