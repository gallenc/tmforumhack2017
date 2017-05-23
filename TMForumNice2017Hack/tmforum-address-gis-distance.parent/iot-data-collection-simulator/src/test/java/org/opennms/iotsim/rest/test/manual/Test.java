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
