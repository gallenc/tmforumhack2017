package org.opennms.tmforum.address.gis.path;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Set;

import org.junit.Test;
import org.opennms.tmforum.address.gis.path.PathModel;
import org.opennms.tmforum.address.model.Address;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class PathModelTest {
	
	private  Set<Address> testData=null;
	private Address startAddress=null;
	

	public void importTestData(){

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream stream = PathModelTest.class.getClassLoader().getResourceAsStream("test-data.json");
			testData = objectMapper.readValue(stream, new TypeReference<Set<Address>>(){});
		} catch (Exception e) {
			throw new RuntimeException("error parsing get addresses reponse: ",e);
		}
		
		startAddress = testData.iterator().next();

		StringBuilder sb = new StringBuilder();
		for(Address address:testData){
			sb.append(address.getStreetNr()+",");
		}
		System.out.println("loaded addresses StreetNr "+sb.toString());
		System.out.println("start address address StreetNr "+startAddress.getStreetNr());
	}
	
	@Test
	public void testcreateAddressRegions(){
		System.out.println("*** Start testcreateAddressRegions()");
		importTestData();
		PathModel pathModel= new PathModel();
		double radius = 10;
		int nsides = 5;
		pathModel.createAddressRegions( testData, radius, nsides);
		
		//assertEquals(testData.size(), pathModel.getAddressRegions().size());
		System.out.println("*** End testcreateAddressRegions()");
		
	}
	
	@Test
	public void testCreateDirectPath(){
		createDirectPath();
	}
	
	public Set<Coordinate> createDirectPath(){
		System.out.println("*** Start testCreateSimplePath()");
		importTestData();
		PathModel pathModel= new PathModel();
		double radius = 10;
		int nsides = 5;
		pathModel.createSortedAddressRegions(startAddress, testData, radius, nsides);
		
		Set<Coordinate> coords = pathModel.directPathCoordinates();
		for(Coordinate coord:coords){
			System.out.println(coord);
		}
		System.out.println("*** End testCreateSimplePath()");
		return coords;
	}
	
	
	@Test
	public void testCreateDirectSplinePath(){
		createDirectSplinePath();
	}
		
	public Set<Coordinate> createDirectSplinePath(){
		System.out.println("*** Start testCreateSimplePath()");
		importTestData();
		PathModel pathModel= new PathModel();
		double radius = 10;
		int nsides = 5;
		pathModel.createSortedAddressRegions(startAddress, testData, radius, nsides);
		
		Set<Coordinate> pathCoordinates = pathModel.directPathCoordinates();
		System.out.println(" number of points in raw line ="+pathCoordinates.size());
		
		Set<Coordinate> coords = PathModel.getSplineCoordinates(pathCoordinates, 0.00);
		System.out.println(" number of points in spline="+coords.size());
		for(Coordinate coord:coords){
			System.out.println(coord);
		}
		System.out.println("*** End testCreateSimplePath()");
		return coords;
	}
	
	@Test
	public void testCreateRegionPath(){
		createRegionPath();
	}
	
	public Set<Coordinate> createRegionPath(){
		System.out.println("*** Start testCreateRegionPath()");
		importTestData();
		PathModel pathModel= new PathModel();
		double radius = 20;
		int nsides = 5;
		pathModel.createSortedAddressRegions(startAddress, testData, radius, nsides);
		
		Set<Coordinate> coords = pathModel.regionPathCoordinates();
		System.out.println(" number of points in raw line ="+coords.size());
		for(Coordinate coord:coords){
			System.out.println(coord);
		}
		System.out.println("*** End testCreateRegionPath()");
		return coords;
	}
	
	@Test
	public void testCreateRegiontSplinePath(){
		createRegionSplinePath();
	}
	
	public Set<Coordinate> createRegionSplinePath(){
		System.out.println("*** Start testCreateRegiontSplinePath()");
		importTestData();
		PathModel pathModel= new PathModel();
		double radius = 10;
		int nsides = 5;
		pathModel.createSortedAddressRegions(startAddress, testData, radius, nsides);
		
		Set<Coordinate> pathCoordinates = pathModel.regionPathCoordinates();
		System.out.println(" number of points in raw line ="+pathCoordinates.size());
		
		Set<Coordinate> coords = PathModel.getSplineCoordinates(pathCoordinates, 0.00);
		System.out.println(" number of points in spline="+coords.size());
		for(Coordinate coord:coords){
			System.out.println(coord);
		}
		System.out.println("*** End testCreateRegiontSplinePath()");
		
		return coords;
	}

	

	@Test
	public void testCircle () {
		System.out.println("*** Start testcircle)");
		PathModel pm= new PathModel();
		double latitude= 50.891099;
		double longitude= 14.9; //-1.390925 ;
		double radius = 0.01;
		int nsides=5;
		Address userData= new Address();
		Polygon sampleArea = pm.createCircle(latitude, longitude, radius, nsides, userData);
		
		Coordinate[] sampleAreaCoordinates = sampleArea.getCoordinates();
		Address sampleAreaUserData = (Address) sampleArea.getUserData();
		System.out.println("userdata:"+sampleAreaUserData);
		for (Coordinate c: sampleAreaCoordinates){
			System.out.println(c);
			
		}
		Point point = sampleArea.getCentroid();
		System.out.println("centroid=" + point.getCoordinate());
		System.out.println("*** End testcircle)");
	}

}
