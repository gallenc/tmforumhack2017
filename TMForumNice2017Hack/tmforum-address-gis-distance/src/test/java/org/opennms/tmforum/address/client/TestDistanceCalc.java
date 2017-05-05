package org.opennms.tmforum.address.client;

import static org.junit.Assert.*;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;

public class TestDistanceCalc {

	@Test
	public void test() {

		// The World Geodetic System (WGS84) is the reference coordinate system used by the Global Positioning System
		CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;
		// the following code is based on JTS.orthodromicDistance( start, end, crs )
	    GeodeticCalculator gc = new GeodeticCalculator(crs);

	    // {"streetNr" : "31", "streetNrSuffix" : "Birth", "streetName" : "Itchen Quays", "streetType" : "Warf", "locality" : "Multi Deck RoRo Terminal", "city" : "Southampton", "stateOrProvince" : "Hampshire", "country" : "UK",   "geoCode" : { "latitude" : "50.889311", "longitude" : "-1.391915" } },
	  //  {"streetNr" : "32", "streetNrSuffix" : "Birth", "streetName" : "Itchen Quays", "streetType" : "Warf", "locality" : "Multi Deck RoRo Terminal", "city" : "Southampton", "stateOrProvince" : "Hampshire", "country" : "UK",   "geoCode" : { "latitude" : "50.891099", "longitude" : "-1.390925" } },

	    
	  //  "latitude" : "50.889311", "longitude" : "-1.391915" 
	  // { "latitude" : "50.891099", "longitude" : "-1.390925" } },
	    
		Coordinate start = new Coordinate(50.889311, -1.391915);
		Coordinate end =  new Coordinate(50.891099, -1.390925);
		try {
			gc.setStartingPosition( JTS.toDirectPosition( start, crs ) );
			gc.setDestinationPosition( JTS.toDirectPosition( end, crs ) );
			
		} catch (TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    double distance = gc.getOrthodromicDistance();
	    
	    int totalmeters = (int) distance;
	    int km = totalmeters / 1000;
	    int meters = totalmeters - (km * 1000);
	    float remaining_cm = (float) (distance - totalmeters) * 10000;
	    remaining_cm = Math.round(remaining_cm);
	    float cm = remaining_cm / 100;

	    System.out.println("Distance = " + km + "km " + meters + "m " + cm + "cm,  distance (m) = "+ distance);
	}

}
