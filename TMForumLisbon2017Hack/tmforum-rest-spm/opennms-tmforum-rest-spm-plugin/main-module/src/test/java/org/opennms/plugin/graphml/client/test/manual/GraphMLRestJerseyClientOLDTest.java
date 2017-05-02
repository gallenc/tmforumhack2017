package org.opennms.plugin.graphml.client.test.manual;

import static org.junit.Assert.*;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.graphdrawing.graphml.GraphmlType;
import org.junit.Test;
import org.opennms.plugins.graphml.client.GraphMLRestJerseyClientOLD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphMLRestJerseyClientOLDTest {
	private static final Logger LOG = LoggerFactory.getLogger(GraphMLRestJerseyClientOLDTest.class);


	private String baseUrl = "http://localhost:8980";
	private String basePath = "/opennms/rest";
	private String userName = "admin"; // If userName is null no basic authentication is generated
	private String password = "admin";

	private String graphname="testgraph1";

	GraphMLRestJerseyClientOLD client = null;

	private GraphMLRestJerseyClientOLD getClient(){
		GraphMLRestJerseyClientOLD client = new GraphMLRestJerseyClientOLD();
		client.setBasePath(basePath);
		client.setBaseUrl(baseUrl);
		client.setPassword(password);
		client.setUserName(userName);
		return client;
	}
	
	@Test
	public void testMarshaller(){
		readTestGraph();
	}

	private GraphmlType readTestGraph(){
		File graphmlfile = new File("./src/test/resources/minimalistic1.xml");
		LOG.debug("reading graph file from"+graphmlfile.getAbsolutePath());

		//GraphmlType graph = JAXB.unmarshal(graphmlfile, GraphmlType.class);
		

		
		
		JAXBContext jaxbContext;
		JAXBElement<GraphmlType> jaxbgraph=null;
		try {
			jaxbContext = JAXBContext.newInstance("org.graphdrawing.graphml");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbgraph =  (JAXBElement<GraphmlType>) jaxbUnmarshaller.unmarshal(graphmlfile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphmlType graph = jaxbgraph.getValue();
		LOG.debug("contents ");
		for (Object x: graph.getGraphOrData()){
			LOG.debug("object "
					+ ""+x.toString()+" name " +x.getClass().getName());
		}
		

		LOG.debug("unmarshaled test graph marshalled from file :"+graphmlTypeToString(graph));
		return graph;
	}
	
	@Test
	public void testsInOrder(){
		LOG.debug("testsInOrder() START");
				
		//testCreateGraph();
		testGetGraph(); 
		//testDeleteGraph();
		LOG.debug("testsInOrder() FINISH");
	}

	//@Test
	public void testCreateGraph() {
		LOG.debug("testCreateGraph() START");
		GraphmlType graphmlType = readTestGraph();

		try {
			GraphMLRestJerseyClientOLD client = getClient();
			client.createGraph(graphname, graphmlType) ;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testCreateGraph() FINISH");

	}

	//@Test 
	public void testDeleteGraph() {
		LOG.debug("testdDeleteGraph() START");
		try {
			GraphMLRestJerseyClientOLD client = getClient();
            client.deleteGraph(graphname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testdDeleteGraph() FINISH");
	}

	//@Test
	public void testGetGraph() {
		LOG.debug("testGetGraph() START");
		try {
			GraphMLRestJerseyClientOLD client = getClient();
			GraphmlType graphmlType = client.getGraph(graphname);

			String gmlstr=null;
			if (graphmlType !=null){
				gmlstr=graphmlTypeToString(graphmlType);
			}

			LOG.debug("returned graphml "+ gmlstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.debug("testGetGraph() FINISH");
	}

	public String graphmlTypeToString(GraphmlType graphmlType){
		try {
			StringWriter sw = new StringWriter();
			JAXBContext ctx = JAXBContext
					.newInstance("org.graphdrawing.graphml");
			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(graphmlType, sw);

			return sw.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
