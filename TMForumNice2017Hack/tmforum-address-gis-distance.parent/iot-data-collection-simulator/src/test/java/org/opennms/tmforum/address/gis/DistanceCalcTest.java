package org.opennms.tmforum.address.gis;

import static org.junit.Assert.*;


import org.junit.Test;
import org.opennms.iotsim.rest.DistanceCalculator;

public class DistanceCalcTest {

	@Test
	public void test() {

		String latitude_start="50.889311";
		String longitude_start="-1.391915";
		String latitude_finish="50.891099";
		String longitude_finish="-1.390925";
		
		double distance = DistanceCalculator.distance(latitude_start, longitude_start, latitude_finish, longitude_finish);
	    
	    int totalmeters = (int) distance;
	    int km = totalmeters / 1000;
	    int meters = totalmeters - (km * 1000);
	    float remaining_cm = (float) (distance - totalmeters) * 10000;
	    remaining_cm = Math.round(remaining_cm);
	    float cm = remaining_cm / 100;

	    System.out.println("Distance = " + km + "km " + meters + "m " + cm + "cm,  distance (m) = "+ distance);
	}

}
