package org.opennms.tmforum.address.client;

import static org.junit.Assert.*;


import org.junit.Test;

import org.opennms.tmforum.address.gis.rest.DistanceCalculator;

public class TestDistanceCalc {

	@Test
	public void test() {

		String latitude_a="50.889311";
		String longitude_a="-1.391915";
		String latitude_b="50.891099";
		String longitude_b="-1.390925";
		
		double distance = DistanceCalculator.distance(latitude_a, longitude_a, latitude_b, longitude_b);
	    
	    int totalmeters = (int) distance;
	    int km = totalmeters / 1000;
	    int meters = totalmeters - (km * 1000);
	    float remaining_cm = (float) (distance - totalmeters) * 10000;
	    remaining_cm = Math.round(remaining_cm);
	    float cm = remaining_cm / 100;

	    System.out.println("Distance = " + km + "km " + meters + "m " + cm + "cm,  distance (m) = "+ distance);
	}

}
