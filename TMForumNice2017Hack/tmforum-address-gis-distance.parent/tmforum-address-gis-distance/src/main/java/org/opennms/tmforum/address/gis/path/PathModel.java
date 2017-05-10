package org.opennms.tmforum.address.gis.path;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.geotools.geometry.jts.GeometryBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opennms.tmforum.address.gis.rest.NearestAddressFinder;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;
import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class PathModel {

	private Set<Polygon> addressRegions = new LinkedHashSet<Polygon>();

	public Set<Polygon> getAddressRegions() {
		return addressRegions;
	}

	/** 
	 * creates address regions following order of static list of addresses
	 * 
	 * @param addresses
	 * @param radius
	 * @param nsides
	 */
	public void createAddressRegions(Set<Address> addresses, double radius, int nsides){
		for(Address address: addresses){
			GeoCode geocode = address.getGeoCode();
			if(geocode!=null){
				double latitude=Double.parseDouble(geocode.getLatitude());
				double longitude=Double.parseDouble(geocode.getLongitude());
				Polygon circle = createCircle(latitude, longitude, radius, nsides, address);
				addressRegions.add(circle);
			}
		}
	}
	
	/**
	 * creates address regions trying to find shortest path between regions
	 * start address may be contained in address collection or not but will always be in final path
	 * @param addresses
	 * @param radius
	 * @param nsides
	 */
	public void createSortedAddressRegions(Address startAddress, Set<Address> addresses, double radius, int nsides){
		NearestAddressFinder naf = new NearestAddressFinder();
		Set<Address> unsortedAddresses = new LinkedHashSet<Address>(addresses);
		Set<Address> sortedAddresses = new LinkedHashSet<Address>();
		
		Address start=startAddress;
		sortedAddresses.add(startAddress);
		if(unsortedAddresses.contains(startAddress)) unsortedAddresses.remove(startAddress);
		while (! unsortedAddresses.isEmpty()){
			naf.setAddressCache(unsortedAddresses);
			DistanceMessage distanceMessage = naf.findNearestAddress(start);
			Address nearestAddress = distanceMessage.getAddress_finish();
			sortedAddresses.add(nearestAddress);
			unsortedAddresses.remove(nearestAddress);
		}
		
		createAddressRegions(sortedAddresses, radius, nsides);
	}

	/**
	 * simply move between centroid of address regions
	 * @return
	 */
	public Set<Coordinate> directPathCoordinates(){
		Set<Coordinate> pathCoordinates = new LinkedHashSet<Coordinate>();
		for(Polygon addressRegion: addressRegions){
			Point point = addressRegion.getCentroid();
			pathCoordinates.add(point.getCoordinate());
		}
		return  pathCoordinates;
	}

	/**
	 * return path which circles each address region
	 * @return
	 */
	public Set<Coordinate> regionPathCoordinates(){
		Set<Coordinate> pathCoordinates = new LinkedHashSet<Coordinate>();
		for(Polygon addressRegion: addressRegions){
			Coordinate[] regionPathCoordinates = addressRegion.getCoordinates(); //TODO
			for(Coordinate coo:regionPathCoordinates) System.out.println("debug:"+coo);
			List<Coordinate> coordinatesList = Arrays.asList(regionPathCoordinates);
			pathCoordinates.addAll(coordinatesList);
		}
		return  pathCoordinates;

	}

	/**
	 * Creates approximation to circle with number of points given by nsides
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @param nsides
	 * @param userData
	 * @return
	 */
	public static Polygon createCircle(double latitude, double longitude, double  radius, int nsides, Object userData) {
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);
		GeometryBuilder gb = new GeometryBuilder(geometryFactory );
		// see https://gis.stackexchange.com/questions/66293/jts-java-topology-suite-calculate-radius-from-a-given-point
		double rad = (radius * 0.00001)/1.1132;
		Polygon circle =  gb.circle(latitude, longitude, rad, nsides );
		circle.setUserData(userData);
		return circle;
	}

	/** 
	 * Returns smoother version of coordinates
	 * @param pathCoordinates
	 * @param fit range 0 (loose fit) to 1 (tightest fit).
	 * @return
	 */
	public static Set<Coordinate> getSplineCoordinates(Set<Coordinate> pathCoordinates, double fit){
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);

		Coordinate[] path = pathCoordinates.toArray(new Coordinate[0]);

		LineString line = geometryFactory.createLineString(path);

		/*
		 * Now we use the GeoTools JTS utility class to smooth the
		 * line. The returned Geometry will have all of the vertices
		 * of the input line plus extra vertices tracing a spline
		 * curve. The second argument is the 'fit' parameter which
		 * can be in the range 0 (loose fit) to 1 (tightest fit).
		 */
		Geometry geometry = JTS.smooth(line, fit);
		Coordinate[] lineCoordinates = geometry.getCoordinates();
		List<Coordinate> coordinatesList = Arrays.asList(lineCoordinates);

		Set<Coordinate> splineCoordinates = new LinkedHashSet<Coordinate>(coordinatesList);
		return splineCoordinates;

	}

}
