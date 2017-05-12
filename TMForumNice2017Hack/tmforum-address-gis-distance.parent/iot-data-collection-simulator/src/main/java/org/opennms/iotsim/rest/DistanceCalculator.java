package org.opennms.iotsim.rest;



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
	 * @param latitude_start
	 * @param longitude_start
	 * @param latitude_finish
	 * @param longitude_finish
	 * @return distance in meters
	 */
	public static double distance( String latitude_start, String longitude_start, String latitude_finish,  String longitude_finish) {
		if(latitude_start==null || latitude_start.isEmpty() || longitude_start==null || longitude_start.isEmpty()) 
			throw new IllegalArgumentException("parameters latitude_start and longitude_start must be set");
		if(latitude_finish==null || latitude_finish.isEmpty() || longitude_finish==null || longitude_finish.isEmpty()) 
			throw new IllegalArgumentException("parameters latitude_finish and longitude_finish must be set");

		try{ 
			double latitudea = Double.parseDouble(latitude_start);
			double latitudeb = Double.parseDouble(latitude_finish);
			double longitudea = Double.parseDouble(longitude_start);
			double longitudeb = Double.parseDouble(longitude_finish);

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
					+ " latitude_start=" + latitude_start
					+ " longitude_start="+ longitude_start
					+ " latitude_finish=" + latitude_finish
					+ " longitude_finish="+ longitude_finish,e);
		}

	}

}
