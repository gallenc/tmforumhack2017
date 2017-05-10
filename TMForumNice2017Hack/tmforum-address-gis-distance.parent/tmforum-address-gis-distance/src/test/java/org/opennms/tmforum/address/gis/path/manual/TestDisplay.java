package org.opennms.tmforum.address.gis.path.manual;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.CircularString;
import org.geotools.geometry.jts.CurvedGeometryFactory;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.Geometry;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.geotools.geometry.jts.GeometryBuilder;

public class TestDisplay {





	@Test
	public void test() {

		SimpleFeature feature1 = createFlightPathFeature();
		SimpleFeature feature2 = createBirthRadiusFeature();

		//	SimpleFeatureCollection simpleFeatureCollection = new DefaultFeatureCollection("internal",TYPE);
		SimpleFeatureCollection simpleFeatureCollection = new DefaultFeatureCollection("internal");
		
		//((DefaultFeatureCollection) simpleFeatureCollection).add(feature1);   
		((DefaultFeatureCollection) simpleFeatureCollection).add(feature2); 

		SimpleFeatureSource featureSource = new CollectionFeatureSource(simpleFeatureCollection);

		MapContent map =buildMap(featureSource);
		saveImage(map, "./target/geotoolsImage.jpg", 800);
		displayMap(map);

		

	}

	public Polygon createCircle(double latitude, double longitude, double  radius) {
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);
		GeometryBuilder gb = new GeometryBuilder(geometryFactory );
		int nsides = 5;
		return gb.circle(latitude, longitude, radius, nsides );
		
//		GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
//		shapeFactory.setNumPoints(32);
//		shapeFactory.setCentre(new Coordinate(latitude, longitude));
//		shapeFactory.setSize(RADIUS * 2);
//		return shapeFactory.createCircle();
	}

	public Polygon createPolygon(){
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);
		Coordinate[] coords  =
				new Coordinate[] {new Coordinate(4, 0), new Coordinate(2, 2),
						new Coordinate(4, 4), new Coordinate(6, 2), new Coordinate(4, 0) };

		LinearRing ring = geometryFactory.createLinearRing( coords );
		LinearRing holes[] = null; // use LinearRing[] to represent holes
		Polygon polygon = geometryFactory.createPolygon(ring, holes );
		return  polygon;
	}
	
	public MultiPolygon createbirths(){
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);
		GeometryBuilder gb = new GeometryBuilder(geometryFactory );
		
		List<Polygon> poly = new ArrayList<Polygon>();

		Polygon[] array = poly.toArray(new Polygon[0]);
		return gb.multiPolygon(array);
	}

	public CircularString createCircularString(){
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		CurvedGeometryFactory curvedFactory = new CurvedGeometryFactory(geometryFactory,Double.MAX_VALUE);

		PackedCoordinateSequence coords = new PackedCoordinateSequence.Double(
				new double[]{10,14,6,10,14,10}, 2 );

		CircularString arc = (CircularString) curvedFactory.createCurvedGeometry(coords);
		return arc;
	}



	public LineString  createLine(){

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(JTSFactoryFinder.EMPTY_HINTS);
		Coordinate[] coordinates = new Coordinate[2];

		double latStart = 44.9;
		double lonStart = 14.9;
		double latEnd = 12.1;
		double lonEnd = 9.4;

		coordinates[0] = new Coordinate(lonStart, latStart);
		coordinates[1] = new Coordinate(lonEnd, latEnd);

		LineString line = geometryFactory.createLineString(coordinates );

		return line;
	}

	public SimpleFeature createFlightPathFeature(){
		SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
		featureTypeBuilder.setName( "LineFeature" );

		//add a geometry property
		featureTypeBuilder.setCRS( DefaultGeographicCRS.WGS84 ); // set crs first
		featureTypeBuilder.add( "line", LineString.class ); // then add geometry

		//build the type
		final SimpleFeatureType TYPE = featureTypeBuilder.buildFeatureType();
		SimpleFeatureBuilder simpleFeatureBuilder = new SimpleFeatureBuilder(TYPE);


		//SimpleFeature polygon = createPolygon();
		//simpleFeatureBuilder.add(polygon);
		//simpleFeatureBuilder.add(line);
		simpleFeatureBuilder.add(createCircularString());


		SimpleFeature featureLine = simpleFeatureBuilder.buildFeature(null);

		return featureLine;
	}

	public SimpleFeature createBirthRadiusFeature(){
		SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
		featureTypeBuilder.setName( "BirthRadius" );

		//add a geometry property
		featureTypeBuilder.setCRS( DefaultGeographicCRS.WGS84 ); // set crs first
		featureTypeBuilder.add( "circle", LineString.class ); // then add geometry

		//build the type
		final SimpleFeatureType TYPE = featureTypeBuilder.buildFeatureType();
		SimpleFeatureBuilder simpleFeatureBuilder = new SimpleFeatureBuilder(TYPE);

		//{ "latitude" : "50.891099", "longitude" : "-1.390925" }
		double latitude= 50.891099;
		double longitude= 14.9; //-1.390925 ;
		final double RADIUS = 10;
		//SimpleFeature polygon = createPolygon();
		simpleFeatureBuilder.add(createCircle(latitude, longitude, RADIUS));
		//simpleFeatureBuilder.add(line);
		//simpleFeatureBuilder.add(createCircularString());


		SimpleFeature featureLine = simpleFeatureBuilder.buildFeature(null);

		return featureLine;
	}



	private MapContent buildMap(SimpleFeatureSource featureSource){
		// Create a map content and add our shapefile to it
		MapContent map = new MapContent();
		map.setTitle("Quickstart");

		//float lineWidth = 2.0f;
		//Style lineStyle = SLD.createLineStyle(Color.red, lineWidth);

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);

		map.addLayer(layer);
		return map;
	}



	public void saveImage(final MapContent map, final String file, final int imageWidth) {

		GTRenderer renderer = new StreamingRenderer();
		renderer.setMapContent(map);

		Rectangle imageBounds = null;
		ReferencedEnvelope mapBounds = null;
		try {
			mapBounds = map.getMaxBounds();
			double heightToWidth = mapBounds.getSpan(1) / mapBounds.getSpan(0);
			imageBounds = new Rectangle(
					0, 0, imageWidth, (int) Math.round(imageWidth * heightToWidth));

		} catch (Exception e) {
			// failed to access map layers
			throw new RuntimeException(e);
		}

		BufferedImage image = new BufferedImage(imageBounds.width, imageBounds.height, BufferedImage.TYPE_INT_RGB);

		Graphics2D gr = image.createGraphics();
		gr.setPaint(Color.WHITE);
		gr.fill(imageBounds);

		try {
			renderer.paint(gr, imageBounds, mapBounds);
			File fileToSave = new File(file);
			ImageIO.write(image, "jpeg", fileToSave);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}



	private void displayMap(MapContent map){

		// Now display the map
		JMapFrame.showMap(map);

		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
