package org.opennms.plugins.graphml.asset.test.manual;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Test;
import org.opennms.netmgt.model.OnmsAssetRecord;
import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsGeolocation;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.monitoringLocations.OnmsMonitoringLocation;

public class NodeTest {

	@Test
	public void test() {
		OnmsNode n = new OnmsNode();
		
//		Set<OnmsCategory> categories = n.getCategories();
//		OnmsCategory cat;
//		cat.
//		n.getCdpElement();
//		n.getForeignId();
//		n.getForeignSource();
//
//		n.getLabel();
//		OnmsMonitoringLocation monloc = n.getLocation();
//		monloc.;
//		n.getNodeId();
//		n.getParent();

		
		
		OnmsAssetRecord assetRecord= new OnmsAssetRecord() ;
		
		OnmsGeolocation gl = assetRecord.getGeolocation();
		Method[] glmethods = gl.getClass().getMethods();
		System.out.println("geolocation");
		for (Method m : glmethods){
			if (m.getName().startsWith("get")){
				if(m.getReturnType().equals(String.class)){
					System.out.print(m.getName());
					try {
						String geovalue = (String) m.invoke(gl);
						System.out.println("   "+geovalue);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	    System.out.println("asset");
		gl.getState();
		Float lat = gl.getLatitude();
		Float lon = gl.getLongitude();

		Method[] methods = assetRecord.getClass().getMethods();
		for (Method arm : methods){
			if (arm.getName().startsWith("get")){
				if(arm.getReturnType().equals(String.class)){
					System.out.print(arm.getName());
					try {
						String assetValue = (String) arm.invoke(assetRecord);
						System.out.println("   "+assetValue);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
