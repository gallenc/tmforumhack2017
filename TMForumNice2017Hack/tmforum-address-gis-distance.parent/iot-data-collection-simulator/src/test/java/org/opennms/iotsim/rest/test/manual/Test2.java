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
