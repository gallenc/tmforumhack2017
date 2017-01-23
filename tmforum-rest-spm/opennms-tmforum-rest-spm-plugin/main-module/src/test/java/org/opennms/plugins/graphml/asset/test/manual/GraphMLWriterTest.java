package org.opennms.plugins.graphml.asset.test.manual;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.graphdrawing.graphml.DataType;
import org.graphdrawing.graphml.EdgeType;
import org.graphdrawing.graphml.GraphType;
import org.graphdrawing.graphml.GraphmlType;
import org.graphdrawing.graphml.HyperedgeType;
import org.graphdrawing.graphml.KeyForType;
import org.graphdrawing.graphml.KeyType;
import org.graphdrawing.graphml.KeyTypeType;
import org.graphdrawing.graphml.NodeType;
import org.graphdrawing.graphml.PortType;
import org.junit.Test;

public class GraphMLWriterTest {

	@Test
	public void test() {
		GraphmlType graphmlType = addkeytypes();

		graphmlType = createGraph(graphmlType);

		// File file = new File("./target/output.xml");

		// /System.out.println("file written to:"+file.getAbsolutePath());
		try {
			StringWriter sw = new StringWriter();
			JAXBContext ctx = JAXBContext
					.newInstance("org.graphdrawing.graphml");
			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(graphmlType, sw);

			System.out.println(sw.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// JAXB.marshal(graphmlType, file);

	}

	public GraphmlType addkeytypes() {

		GraphmlType graphmlType = new GraphmlType();

		// <key id="label" for="graphml" attr.name="label"
		// attr.type="string"></key>
		KeyType graphmlLabel = new KeyType();
		graphmlLabel.setAttrName("label");
		graphmlLabel.setAttrType(KeyTypeType.STRING);
		graphmlLabel.setId("label");
		graphmlLabel.setFor(KeyForType.GRAPHML);
		graphmlType.getGraphOrData().add(graphmlLabel);

		// <key id="label" for="graph" attr.name="label"
		// attr.type="string"></key>
		KeyType graphLabelKey = new KeyType();
		graphLabelKey.setAttrName("label");
		graphLabelKey.setAttrType(KeyTypeType.STRING);
		graphLabelKey.setId("label");
		graphLabelKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(graphLabelKey);

		// <key id="description" for="graph" attr.name="description"
		// attr.type="string"></key>
		KeyType graphDescriptionKey = new KeyType();
		graphDescriptionKey.setAttrName("description");
		graphDescriptionKey.setAttrType(KeyTypeType.STRING);
		graphDescriptionKey.setId("description");
		graphDescriptionKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(graphDescriptionKey);

		// <key id="namespace" for="graph" attr.name="namespace"
		// attr.type="string"></key>
		KeyType graphNamespacekey = new KeyType();
		graphNamespacekey.setAttrName("namsepace");
		graphNamespacekey.setAttrType(KeyTypeType.STRING);
		graphNamespacekey.setId("namespace");
		graphNamespacekey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(graphNamespacekey);

		// <key id="preferred-layout" for="graph" attr.name="preferred-layout"
		// attr.type="string"></key>
		KeyType preferredLayoutKey = new KeyType();
		preferredLayoutKey.setAttrName("preferred-layout");
		preferredLayoutKey.setAttrType(KeyTypeType.STRING);
		preferredLayoutKey.setId("preferred-layout");
		preferredLayoutKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(preferredLayoutKey);

		// <key id="focus-strategy" for="graph" attr.name="focus-strategy"
		// attr.type="string"></key>
		KeyType focusStrategyKey = new KeyType();
		focusStrategyKey.setAttrName("focus-strategy");
		focusStrategyKey.setAttrType(KeyTypeType.STRING);
		focusStrategyKey.setId("focus-strategy");
		focusStrategyKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(focusStrategyKey);

		// <key id="focus-ids" for="graph" attr.name="focus-ids"
		// attr.type="string"></key>
		KeyType focusIdsKey = new KeyType();
		focusIdsKey.setAttrName("focus-ids");
		focusIdsKey.setAttrType(KeyTypeType.STRING);
		focusIdsKey.setId("focus-ids");
		focusIdsKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(focusIdsKey);

		// <key id="semantic-zoom-level" for="graph"
		// attr.name="semantic-zoom-level" attr.type="int"/>
		KeyType semanticZoomlevelKey = new KeyType();
		semanticZoomlevelKey.setAttrName("semantic-zoom-level");
		semanticZoomlevelKey.setAttrType(KeyTypeType.STRING);
		semanticZoomlevelKey.setId("semantic-zoom-level");
		semanticZoomlevelKey.setFor(KeyForType.GRAPH);
		graphmlType.getGraphOrData().add(semanticZoomlevelKey);

		// <key id="label" for="node" attr.name="label"
		// attr.type="string"></key>
		KeyType nodelabelKey = new KeyType();
		nodelabelKey.setAttrName("label");
		nodelabelKey.setAttrType(KeyTypeType.STRING);
		nodelabelKey.setId("label");
		nodelabelKey.setFor(KeyForType.NODE);
		graphmlType.getGraphOrData().add(nodelabelKey);

		// key id="foreignSource" for="node" attr.name="foreignSource"
		// attr.type="string"></key>
		KeyType foreignSourcekey = new KeyType();
		foreignSourcekey.setAttrName("foreignSource");
		foreignSourcekey.setAttrType(KeyTypeType.STRING);
		foreignSourcekey.setId("foreignSource");
		foreignSourcekey.setFor(KeyForType.NODE);
		graphmlType.getGraphOrData().add(foreignSourcekey);

		// <key id="foreignID" for="node" attr.name="foreignID"
		// attr.type="string"></key>
		KeyType foreignIDkey = new KeyType();
		foreignIDkey.setAttrName("foreignID");
		foreignIDkey.setAttrType(KeyTypeType.STRING);
		foreignIDkey.setId("foreignID");
		foreignIDkey.setFor(KeyForType.NODE);
		graphmlType.getGraphOrData().add(foreignIDkey);

		// <key id="nodeID" for="node" attr.name="nodeID" attr.type="int"></key>
		KeyType nodeIdKey = new KeyType();
		nodeIdKey.setAttrName("nodeID");
		nodeIdKey.setAttrType(KeyTypeType.INT);
		nodeIdKey.setId("nodeID");
		nodeIdKey.setFor(KeyForType.NODE);
		graphmlType.getGraphOrData().add(nodeIdKey);

		return graphmlType;

	}

	private GraphmlType createGraph(GraphmlType graphmlType) {

		// <!-- shows up in the menu -->
		// <data key="label">Minimalistic GraphML Topology Provider</data>

		DataType menuLabel = new DataType();
		menuLabel.setKey("label");
		menuLabel.setId("Asset GraphML Topology Provider");
		graphmlType.getGraphOrData().add(menuLabel);
		
		// <graph id="graph1">
		GraphType graph=new GraphType();
		graph.setId("graph1");
		graphmlType.getGraphOrData().add(graph);


		// <data key="namespace">minimalistic</data>
		DataType namespace = new DataType();
		namespace.setKey("namespace");
		namespace.setId("minimalistic");
		graph.getDataOrNodeOrEdge().add(namespace);
		
		// <node id="node2-camera">
		NodeType node = new NodeType();
		node.setId("node2-camera");
		graph.getDataOrNodeOrEdge().add(node);
		
		// <!-- <data key="label">North by north west</data> -->
		DataType nodeLabel = new DataType();
		nodeLabel.setKey("label");
		nodeLabel.setId("North by north west");
		node.getDataOrPort().add(nodeLabel);
		
		// <data key="foreignSource">opennms-test</data>
		DataType foreignSource = new DataType();
		foreignSource.setKey("foreignSource");
		foreignSource.setId("opennms-test");
		node.getDataOrPort().add(foreignSource);
		
		// <data key="foreignID">1483632606160</data>
		DataType foreignID = new DataType();
		foreignID.setKey("foreignID");
		foreignID.setId("1483632606160");
		node.getDataOrPort().add(foreignID);
		
		// <data key="nodeID">1</data>
		DataType nodeID = new DataType();
		nodeID.setKey("nodeID");
		nodeID.setId("1");
		node.getDataOrPort().add(nodeID);

		return graphmlType;
	}
	
	

}
