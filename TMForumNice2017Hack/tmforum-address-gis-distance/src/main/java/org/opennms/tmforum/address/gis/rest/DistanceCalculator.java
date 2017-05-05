package org.opennms.tmforum.address.gis.rest;



import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


import com.vividsolutions.jts.geom.Coordinate;

public class DistanceCalculator {
	// The World Geodetic System (WGS84) is the reference coordinate system used by the Global Positioning System
	private	static CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;

	/**
	 * Calculate the distance between 2 points a and b given lat and long gis coordinates for a and b
	 * Calculation based upon The World Geodetic System (WGS84) which is the reference coordinate system 
	 * used by the Global Positioning System
	 * @param latitude_a
	 * @param longitude_a
	 * @param latitude_b
	 * @param longitude_b
	 * @return distance in meters
	 */
	public static double distance( String latitude_a, String longitude_a, String latitude_b,  String longitude_b) {
		if(latitude_a==null || latitude_a.isEmpty() || longitude_a==null || longitude_a.isEmpty()) 
			throw new IllegalArgumentException("parameters latitude_a and longitude_a must be set");
		if(latitude_b==null || latitude_b.isEmpty() || longitude_b==null || longitude_b.isEmpty()) 
			throw new IllegalArgumentException("parameters latitude_b and longitude_b must be set");

		try{ 
			double latitudea = Double.parseDouble(latitude_a);
			double latitudeb = Double.parseDouble(latitude_b);
			double longitudea = Double.parseDouble(longitude_a);
			double longitudeb = Double.parseDouble(longitude_b);

			// the following code is based on JTS.orthodromicDistance( start, end, crs )
			GeodeticCalculator gc = new GeodeticCalculator(crs);

			Coordinate start = new Coordinate(latitudea, longitudea);
			Coordinate end =  new Coordinate(latitudeb, longitudeb);

			gc.setStartingPosition( JTS.toDirectPosition( start, crs ) );
			gc.setDestinationPosition( JTS.toDirectPosition( end, crs ) );

			double distance = gc.getOrthodromicDistance();
			
			return distance;

		} catch (Exception e) {
			throw new IllegalArgumentException("unable to parse latitude and longitude arguments"
					+ " latitude_a=" + latitude_a
					+ " longitude_a="+ longitude_a
					+ " latitude_b=" + latitude_b
					+ " longitude_b="+ longitude_b,e);
		}

	}

}
