package org.openoss.karaf.features.tmforum.spm.ri.client.test.manual;

import static org.junit.Assert.*;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.openoss.karaf.features.tmforum.spm.impl.client.rest.SPMRestJerseyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SPMRestJerseyClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(SPMRestJerseyClientTest.class);


	private String baseUrl = "http://localhost:8980";
	private String basePath = "/opennms/rest";
	private String userName = "admin"; // If userName is null no basic authentication is generated
	private String password = "admin";

	private String graphname="testgraph1";

	SPMRestJerseyClient client = null;

	private SPMRestJerseyClient getClient(){
		SPMRestJerseyClient client = new SPMRestJerseyClient();
		client.setBasePath(basePath);
		client.setBaseUrl(baseUrl);
		client.setPassword(password);
		client.setUserName(userName);
		return client;
	}
	

	
	@Test
	public void testsInOrder(){
		LOG.debug("testsInOrder() START");
				

		LOG.debug("testsInOrder() FINISH");
	}



}
