package org.opennms.tmforum.address.gis.path.manual;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.geotools.data.collection.CollectionFeatureSource;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
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
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opennms.tmforum.address.gis.path.PathModelTest;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class PathModelTestDisplay {

	@Test
	public void test() {
		SimpleFeatureCollection simpleFeatureCollection = new DefaultFeatureCollection("internal");

		Set<Coordinate> pathCoordinates = new PathModelTest().createRegiontSplinePath();
		System.out.println("pathCoordinates created:"+pathCoordinates.size());
		
		SimpleFeature feature = createFeature( pathCoordinates);

		((DefaultFeatureCollection) simpleFeatureCollection).add(feature); 

		SimpleFeatureSource featureSource = new CollectionFeatureSource(simpleFeatureCollection);

		MapContent map =buildMap(featureSource);
		saveImage(map, "./target/geotoolsImage.jpg", 800);
		displayMap(map);
	}
	
	public SimpleFeature createFeature(Set<Coordinate> pathCoordinates){
		Coordinate[] path = pathCoordinates.toArray(new Coordinate[0]);

		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		LineString line = geometryFactory.createLineString(path);

		SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
		featureTypeBuilder.setName( "test feature" );

		//add a geometry property
		featureTypeBuilder.setCRS( DefaultGeographicCRS.WGS84 ); // set crs first
		featureTypeBuilder.add( "circle", LineString.class ); // then add geometry

		//build the type
		final SimpleFeatureType TYPE = featureTypeBuilder.buildFeatureType();
		SimpleFeatureBuilder simpleFeatureBuilder = new SimpleFeatureBuilder(TYPE);
		
		simpleFeatureBuilder.add(line);

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
