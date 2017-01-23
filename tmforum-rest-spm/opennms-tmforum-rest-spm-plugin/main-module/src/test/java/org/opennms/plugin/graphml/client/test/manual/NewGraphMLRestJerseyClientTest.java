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

import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.graphdrawing.graphml.xmlns.ObjectFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewGraphMLRestJerseyClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(NewGraphMLRestJerseyClientTest.class);


	
	@Test
	public void testMarshaller(){
		readTestGraph();
	}

	private GraphmlType readTestGraph(){
		File graphmlfile = new File("./src/test/resources/test-graph.xml");
		LOG.debug("reading graph file from"+graphmlfile.getAbsolutePath());

		//GraphmlType graph = JAXB.unmarshal(graphmlfile, GraphmlType.class);

		JAXBContext ctx;
		JAXBElement<GraphmlType> jaxbgraph=null;
		try {
			ctx = JAXBContext.newInstance("org.graphdrawing.graphml.xmlns");
			Unmarshaller jaxbUnmarshaller = ctx.createUnmarshaller();
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
	


	public String graphmlTypeToString(GraphmlType graphmlType){
		try {
			
		      // Marshal graphmlType
	        ObjectFactory objectFactory = new ObjectFactory();
	        JAXBElement<GraphmlType> je =  objectFactory.createGraphml(graphmlType);
	        
			StringWriter sw = new StringWriter();
			JAXBContext ctx = JAXBContext.newInstance("org.graphdrawing.graphml.xmlns");

			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(je, sw);

			return sw.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
