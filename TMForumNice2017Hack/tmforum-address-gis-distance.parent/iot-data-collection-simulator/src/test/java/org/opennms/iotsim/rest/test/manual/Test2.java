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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.opennms.iotsim.model.KeyValuePair;

public class Test2 {

	//@Test
	public void test() {
		//2pi radians equals 360 degrees
		System.out.println(2*Math.PI);
		for (double a = 0;a<=2*Math.PI;a=a+0.5){
			double x = Math.sin(a);
		System.out.println(a+"  "+x+"  "+Math.asin(x));
		}
	}
	
	@Test
	public void test2() {
		
	long timestamp = new Date().getTime();
//	long lasttimestamp =  timestamp-30*1000;
//		long msSinceLastSample = timestamp - lasttimestamp;
//
		List<KeyValuePair> outparameters = new ArrayList<KeyValuePair>();
		
		
		
		double fivemin = 5*60*60*1000 ;
		
		double modtime = timestamp % fivemin;
		
		double t1 = modtime / fivemin;
		
		double pi2 = 2*Math.PI;
		
		System.out.println(" "+timestamp+" "+modtime+" "+t1);
		
	//	x = msSinceLastSample / fivemin ;

//		
//		double si = msSinceLastSample / timestamp;
//		double pi2 = 2*Math.PI;
//		System.out.println(" "+timestamp+" "+lasttimestamp+" "+msSinceLastSample+" "+si+"  "+pi2+"  "+si % pi2  );
//		
		
		
		//System.out.println(a+"  "+x+"  "+Math.asin(x));
		
//		for (double a = 0;a<=2*Math.PI;a=a+0.5){
//			double x = Math.sin(a);
//		System.out.println(a+"  "+x+"  "+Math.asin(x));
//		}
	}
	
	

}
