package org.opennms.plugins.graphml.asset;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.model.OnmsAssetRecord;
import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsGeolocation;
import org.opennms.netmgt.model.OnmsNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionOperations;


public class NodeInfoRepository {
	private static final Logger LOG = LoggerFactory.getLogger(NodeInfoRepository.class);

	private volatile NodeDao nodeDao;
	private volatile TransactionOperations transactionOperations;

	/**
	 * Map of Maps of node parameters which is populated by populateBodyWithNodeInfo
	 * nodeKey is a unique identifier for the node from nodeid and/or node foreignsource or foreignid
	 * node_parameterKey is the parameter name e.g. foreignsource or foreignid
	 * node_parameterValue is the paramter value for the given key
	 * Map<nodeKey,Map<node_parameterKey,node_parameterValue>> nodeInfo
	 */
	private Map<String,Map<String,String>> nodeInfo = Collections.synchronizedMap(new LinkedHashMap<String, Map<String, String>>());

	/* getters and setters */
	public Map<String, Map<String, String>> getNodeInfo() {
		return nodeInfo;
	}

	public NodeDao getNodeDao() {
		return nodeDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public TransactionOperations getTransactionOperations() {
		return transactionOperations;
	}

	public void setTransactionOperations(TransactionOperations transactionOperations) {
		this.transactionOperations = transactionOperations;
	}

	/** 
	 * utility method to clear nodeInfo table
	 */
	private void clearNodeInfo(){
		// make sure nodeInfo is empty
		for(String nodeKey:nodeInfo.keySet()){
			Map<String, String> param = nodeInfo.get(nodeKey);
			if (param !=null) param.clear();
		};
		nodeInfo.clear();
	}

	/**
	 * initialises node info map from the opennms database node and asset tables using nodeDao
	 */
	public synchronized void initialiseNodeInfo() {
		if (nodeDao==null) throw new RuntimeException("nodeDao must be set before running initialiseNodeInfo");
		LOG.info("initialising node info");

		// make sure nodeInfo is empty
		clearNodeInfo();

		// populate nodeinfo from latest database provisioned nodes information
		// wrap in a transaction so that Hibernate session is bound and getCategories works
		transactionOperations.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<OnmsNode> nodeList = nodeDao.findAllProvisionedNodes();
				initialiseNodeInfoFromNodeList(nodeList);
			}
		});       
	}

	/**
	 * initialises node info map from supplied opennms nodeList
	 * @param nodeList
	 */
	public synchronized void initialiseNodeInfoFromNodeList(List<OnmsNode> nodeList) {
		if (nodeList==null) throw new RuntimeException("nodeList must note be null");

		// make sure nodeInfo is empty
		clearNodeInfo();

		// populate nodeinfo from supplieed nodeList information
		for (OnmsNode node:nodeList){
			Map<String, String> nodeParameters = new LinkedHashMap<String, String>();
			populateNodeParametersWithNodeInfo(nodeParameters , node);
			String nodeId = node.getNodeId();
			nodeInfo.put(nodeId, nodeParameters);
			if (LOG.isDebugEnabled()){
				LOG.debug("\nNodeInfoRepository added nodeId:"+nodeId+" parameters:"+nodeParamatersToString(nodeParameters));
			}
		}
	}


	/**
	 * utility method to list contents of nodeParameters as String for debug
	 * @param nodeParameters
	 * @return single string of parameters for node
	 */
	public String nodeParamatersToString(Map<String, String> nodeParameters){
		if(nodeParameters==null) return null;
		StringBuffer sb=new StringBuffer();
		for( String parameterKey:nodeParameters.keySet()){
			sb.append("\n    nodeParamKey: '"+parameterKey+"' nodeParamValue: '"+nodeParameters.get(parameterKey)+"'");
		}
		return sb.toString();
	};

	/**
	 * utility method to list contents of nodeInfo as String for debug
	 * @return
	 */
	public String nodeInfoToString(){
		StringBuffer sb=new StringBuffer();

		for(String nodeId:nodeInfo.keySet()){
			Map<String, String> nodeParams = nodeInfo.get(nodeId);
			sb.append("\n nodeId: '"+nodeId+"' nodeParameters:"+nodeParamatersToString(nodeParams));
		}
		return sb.toString();
	}


	/**
	 * utility method to populate a Map with the most important node attributes
	 *
	 * @param nodeParameters the map
	 * @param node the node object
	 */
	private void populateNodeParametersWithNodeInfo(Map<String,String> nodeParameters, OnmsNode node) {
		nodeParameters.put("node-nodelabel", node.getLabel());
		nodeParameters.put("node-nodeid", node.getNodeId());
		nodeParameters.put("node-foreignsource", node.getForeignSource());
		nodeParameters.put("node-foreignid", node.getForeignId());
		nodeParameters.put("node-nodesysname", node.getSysName());
		nodeParameters.put("node-nodesyslocation", node.getSysLocation());
		nodeParameters.put("node-operatingsystem", node.getOperatingSystem());

		StringBuilder categories=new StringBuilder();
		for (Iterator<OnmsCategory> i=node.getCategories().iterator();i.hasNext();) {
			categories.append(((OnmsCategory)i.next()).getName());
			if(i.hasNext()) {
				categories.append(",");
			}
		}
		nodeParameters.put("node-categories", categories.toString());

		// parent information
		OnmsNode parent = node.getParent();
		if (parent!=null){
			nodeParameters.put("parent-nodelabel", parent.getLabel());
			nodeParameters.put("parent-nodeid", parent.getNodeId());
			nodeParameters.put("parent-foreignsource", parent.getForeignSource());
			nodeParameters.put("parent-foreignid", parent.getForeignId());
		}

		//assetRecord.
		OnmsAssetRecord assetRecord= node.getAssetRecord() ;
		if(assetRecord!=null){

			//geolocation
			OnmsGeolocation gl = assetRecord.getGeolocation();
			if (gl !=null){
				nodeParameters.put("asset-country", gl.getCountry());
				nodeParameters.put("asset-address1", gl.getAddress1());
				nodeParameters.put("asset-address2", gl.getAddress2());
				nodeParameters.put("asset-city", gl.getCity());
				nodeParameters.put("asset-zip", gl.getZip());
				nodeParameters.put("asset-state", gl.getState());
				nodeParameters.put("asset-latitude", (gl.getLatitude()!=null ? gl.getLatitude().toString() : null));
				nodeParameters.put("asset-longitude", (gl.getLongitude()!=null ? gl.getLongitude().toString() : null));
			}

			//assetRecord
			nodeParameters.put("asset-region", assetRecord.getRegion());
			nodeParameters.put("asset-division", assetRecord.getDivision());
			nodeParameters.put("asset-department", assetRecord.getDepartment());
			nodeParameters.put("asset-building", assetRecord.getBuilding());
			nodeParameters.put("asset-floor", assetRecord.getFloor());
			nodeParameters.put("asset-room",  assetRecord.getRoom());
			nodeParameters.put("asset-rack", assetRecord.getRack());
			nodeParameters.put("asset-slot", assetRecord.getSlot());
			nodeParameters.put("asset-port", assetRecord.getPort());
			nodeParameters.put("asset-circuitid", assetRecord.getCircuitId());

			nodeParameters.put("asset-category", assetRecord.getCategory());
			nodeParameters.put("asset-displaycategory", assetRecord.getDisplayCategory());
			nodeParameters.put("asset-notifycategory", assetRecord.getNotifyCategory());
			nodeParameters.put("asset-pollercategory", assetRecord.getPollerCategory());
			nodeParameters.put("asset-thresholdcategory", assetRecord.getThresholdCategory());
			nodeParameters.put("asset-managedobjecttype", assetRecord.getManagedObjectType());
			nodeParameters.put("asset-managedobjectinstance", assetRecord.getManagedObjectInstance());

			nodeParameters.put("asset-manufacturer", assetRecord.getManufacturer());
			nodeParameters.put("asset-vendor", assetRecord.getVendor());
			nodeParameters.put("asset-modelnumber", assetRecord.getModelNumber());
			nodeParameters.put("asset-description", assetRecord.getDescription());
			nodeParameters.put("asset-operatingsystem", assetRecord.getOperatingSystem()); 


			/*not used or depreciated*/
			/*
                assetRecord.getComment();
                assetRecord.getPassword();
                assetRecord.getConnection();
                //assetRecord.getCountry(); // depreciated
                assetRecord.getUsername();
                assetRecord.getEnable();
                assetRecord.getAutoenable();
                assetRecord.getCpu();
                assetRecord.getRam();
                assetRecord.getSnmpcommunity();
                assetRecord.getRackunitheight();
                assetRecord.getAdmin();
                assetRecord.getAdditionalhardware();
                assetRecord.getInputpower();
                assetRecord.getNumpowersupplies();
                assetRecord.getHdd6();
                assetRecord.getHdd5();
                assetRecord.getHdd4();
                assetRecord.getHdd3();
                assetRecord.getHdd2();
                assetRecord.getHdd1();
                assetRecord.getStoragectrl();
                //assetRecord.getAddress1();// depreciated
                //assetRecord.getAddress2();// depreciated
                //assetRecord.getCity();// depreciated
                //assetRecord.getZip();// depreciated
                assetRecord.getVmwareManagedEntityType();
                assetRecord.getVmwareManagedObjectId();
                assetRecord.getVmwareManagementServer();
                assetRecord.getVmwareState();
                assetRecord.getVmwareTopologyInfo();
                assetRecord.getSerialNumber();
                assetRecord.getAssetNumber();
                assetRecord.getVendorPhone();
                assetRecord.getVendorFax();
                assetRecord.getVendorAssetNumber();
                assetRecord.getLastModifiedBy();
                assetRecord.getDateInstalled();
                assetRecord.getLease();
                assetRecord.getLeaseExpires();
                assetRecord.getSupportPhone();
                assetRecord.getMaintcontract();
                //assetRecord.getMaintContractNumber();// depreciated
                assetRecord.getMaintContractExpiration();
                //assetRecord.getState();// depreciated
			 */
		}

	}

}
