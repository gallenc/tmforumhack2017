package org.opennms.plugins.graphml.asset.test.manual;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opennms.netmgt.model.OnmsAssetRecord;
import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsGeolocation;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.monitoringLocations.OnmsMonitoringLocation;
import org.opennms.plugins.graphml.asset.NodeInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeInfoRepositoryTest {
	private static final Logger LOG = LoggerFactory.getLogger(NodeInfoRepositoryTest.class);

	@Test
	public void test() {
		
		List<OnmsNode> nodeList= new ArrayList<OnmsNode>();
		
		for( int id = 0;id<10;id++){
			OnmsNode n =createNode(id);
			nodeList.add(n);
		}
		
		NodeInfoRepository nir= new NodeInfoRepository();
		nir.initialiseNodeInfoFromNodeList(nodeList);
		
		LOG.debug(nir.nodeInfoToString());
		
	}
	
	/**
	 * utility to create unique node with id
	 * @param id
	 * @return
	 */
	public OnmsNode createNode(int id){
	
		OnmsNode node = new OnmsNode();

		node.setLabel("node-nodelabel_"+id);
		node.setId(id);
		node.setForeignSource("node-foreignsource_"+id);
		node.setForeignId("node-foreignid_"+id);
		node.setSysName("node-nodesysname_"+id);
		node.setSysLocation("node-nodeLocation_"+id);
		node.setOperatingSystem("node-operatingsystem_"+id);

		Set<OnmsCategory> categories=new LinkedHashSet<OnmsCategory>();
		node.setCategories(categories);

		OnmsCategory onmsCategory = new OnmsCategory();
		onmsCategory.setName("category1");
		categories.add(onmsCategory);
		OnmsCategory onmsCategory2 = new OnmsCategory();
		onmsCategory2.setName("category2");
		categories.add(onmsCategory2);

		// parent information
		OnmsNode parent = new OnmsNode();
		node.setParent(parent);
		parent.setLabel("parent-nodelabel");
		parent.setNodeId("1");
		parent.setForeignSource("parent-foreignsource_"+id);
		parent.setForeignId("parent-foreignid_"+id);

		OnmsAssetRecord assetRecord = new OnmsAssetRecord();
		node.setAssetRecord(assetRecord) ;

		OnmsGeolocation gl = new OnmsGeolocation();
		assetRecord.setGeolocation(gl);

		//geolocation
		gl.setCountry("asset-country_"+id);
		gl.setAddress1("asset-address1_"+id);
		gl.setAddress2("asset-address2_"+id);
		gl.setCity("asset-city_"+id);
		gl.setZip	("asset-zip_"+id);
		gl.setState("asset-state_"+id); 
		Float lat= Float.valueOf("0");
		gl.setLatitude(lat);
		Float lng= Float.valueOf("0");
		gl.setLongitude(lng);

		//assetRecord
		assetRecord.setRegion("asset-region_"+id);
		assetRecord.setDivision("asset-division_"+id);
		assetRecord.setDepartment("asset-department_"+id); 
		assetRecord.setBuilding("asset-building_"+id); 
		assetRecord.setFloor("asset-floor_"+id); 
		assetRecord.setRoom("asset-room_"+id);
		assetRecord.setRack("asset-rack_"+id); 
		assetRecord.setSlot("asset-slot_"+id);
		assetRecord.setPort("asset-port_"+id);
		assetRecord.setCircuitId("asset-circuitid_"+id); 

		assetRecord.setCategory("asset-category_"+id); 
		assetRecord.setDisplayCategory("asset-displaycategory_"+id);
		assetRecord.setNotifyCategory("asset-notifycategory_"+id);
		assetRecord.setPollerCategory("asset-pollercategory_"+id);
		assetRecord.setThresholdCategory("asset-thresholdcategory_"+id);
		assetRecord.setManagedObjectType("asset-managedobjecttype_"+id);
		assetRecord.setManagedObjectInstance("asset-managedobjectinstance_"+id); 

		assetRecord.setManufacturer("asset-manufacturer_"+id);
		assetRecord.setVendor("asset-vendor_"+id);
		assetRecord.setModelNumber("asset-modelnumber_"+id); 
		assetRecord.setDescription("asset-description_"+id);
		assetRecord.setOperatingSystem("asset-operatingsystem_"+id); 

		return node;
	}

}
