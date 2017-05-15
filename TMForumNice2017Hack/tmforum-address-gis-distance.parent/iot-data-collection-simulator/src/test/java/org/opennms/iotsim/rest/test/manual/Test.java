package org.opennms.iotsim.rest.test.manual;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

public class Test {

	@org.junit.Test
	public void test(){
		proc("0");
		proc("23");
		proc("23.1234");
		proc("23.123456789");
		proc("23.123456789123456789");
		
	}
	
	public void proc(String d)
	 {
		 System.out.println("original"+d);
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(5);
		df.setMinimumFractionDigits(1);
		df.setMinimumIntegerDigits(1);
	    Double lat = Double.parseDouble(d);
        String latStr = df.format(lat);
        System.out.println("df.format(lat) "+latStr);
        String[] s = latStr.split("\\.");
        String latitudeIntegerDigits = s[0];
        String latitudeFractionalDigits =s[1];
       
        System.out.println("latitudeIntegerDigits :"+latitudeIntegerDigits );
        System.out.println("latitudeFractionalDigits :"+latitudeFractionalDigits );
	}

}
