/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2016 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2016 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.plugins.graphml.client;

import javax.ws.rs.core.MediaType;

import org.graphdrawing.graphml.GraphmlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * this uses old opennms org.graphdrawing.graphml.GraphmlType;
 * simple jersey client for posting, retrieving and deleting grapmML graphs from OpennMS topology provider
 * @author admin
 *
 */
public class GraphMLRestJerseyClientOLD {
	private static final Logger LOG = LoggerFactory.getLogger(GraphMLRestJerseyClientOLD.class);

	private String baseUrl = "http://localhost:8980";
	private String basePath = "/opennms/rest";
	private String userName = null; // If userName is null no basic authentication is generated
	private String password = "";

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * User name to use in basic authentication
	 * If userName is null then no basic authentication is generated
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * password to use in basic authentication.
	 * password must not be set to null but if not set, password will default to empty string "".
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password==null) throw new RuntimeException("password must not be set to null");
		this.password = password;
	}

	/**
	 * base URL of service as http://HOSTNAME:PORT e.g http://localhost:8181
	 * @return baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * base URL of service as http://HOSTNAME:PORT/ e.g http://localhost:8181
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * base path of service starting with '/' such that service is accessed using baseUrl/basePath... 
	 * e.g http://localhost:8181/featuremgr
	 * @return basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * base path of service starting with '/' such that service is accessed using baseUrl/basePath... 
	 * e.g http://localhost:8181/featuremgr
	 * @return basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	private Client newClient(){
		Client client = Client.create();
		if (userName!=null) client.addFilter(new HTTPBasicAuthFilter(userName, password));
		return  client;
	}

	/**
	 * calls opennms rest method to create new graph using a graphmlType
	 * @param graphname
	 * @param graphmlType
	 * @throws GraphmlClientException if graph cannot be created or connectivity fails
	 */
	public void createGraph(String graphname, GraphmlType graphmlType) throws GraphmlClientException {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphmlType==null ) throw new RuntimeException("graphmlType must be set");
		
		Client client = newClient();

		//http://localhost:8980/opennms/rest/graphml/minimalistic1

		String urlStr= baseUrl+basePath+"/graphml/"+graphname;

		WebResource r = client.resource(urlStr);

		// POST method
		ClientResponse response=null;
		try{
			LOG.debug("creating Graph from url="+urlStr);
			response = r
					.type(MediaType.APPLICATION_XML)
					.post(ClientResponse.class, graphmlType);
		}
		catch(Exception e){
			throw new GraphmlClientException("error creating new graph graphname="+graphname, e);
		}

		// check response status code and reply error message
		if (response.getStatus() != 201) { // 201 created
			throw new GraphmlClientException("error creating new graph graphname="+graphname
					+ " response status="+response.getStatus());
		}

	}

	/**
	 * deletes graph with name graphname. GraphmlClientException if graph cannot be deleted because not found or if IO exception
	 * @param graphname
	 * @throws GraphmlClientException
	 */
	public void deleteGraph(String graphname) throws GraphmlClientException {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphname==null || "".equals(graphname)) throw new RuntimeException("graphname must be set");

		Client client = newClient();

		//http://localhost:8980/opennms/rest/graphml/minimalistic1

		String urlStr= baseUrl+basePath+"/graphml/"+graphname;

		WebResource r = client.resource(urlStr);

		// DELETE method
		ClientResponse response=null;
		try{
			LOG.debug("deleting Graph from url="+urlStr);
			response = r
					.type(MediaType.APPLICATION_XML)
					.delete(ClientResponse.class);
		}
		catch(Exception e){
			throw new GraphmlClientException("error deleting new graph graphname="+graphname, e);
		}

		// check response status code and reply error message
		if (response.getStatus() != 200) {
			throw new GraphmlClientException("error deleting graph graphname="+graphname
					+ " response status="+response.getStatus());
		}

	}

	/**
	 * returns a GraphmlType graph from opennms if graph is available. Returns null if graph doesn't exist 
	 * @param graphname
	 * @return
	 * @throws GraphmlClientException
	 */
	public GraphmlType getGraph( String graphname)  throws GraphmlClientException {
		if(baseUrl==null || basePath==null) throw new RuntimeException("basePath and baseUrl must both be set");
		if(graphname==null || "".equals(graphname)) throw new RuntimeException("graphname must be set");

		Client client = newClient();

		GraphmlType graphmlType =null;

		//http://localhost:8980/opennms/rest/graphml/minimalistic1

		String urlStr= baseUrl+basePath+"/graphml/"+graphname;

		WebResource r = client.resource(urlStr);

		// GET method
		ClientResponse response=null;
		try{
			LOG.debug("getting Graph from url="+urlStr);
			response = r.accept(MediaType.APPLICATION_XML)
					.type(MediaType.APPLICATION_XML)
					.get(ClientResponse.class);
		}
		catch(Exception e){
			throw new GraphmlClientException("error getting graph graphname="+graphname, e);
		}

		// check response status code and reply error message
		if (response.getStatus() != 200) {
			return null;
		}

		try{
			graphmlType = response.getEntity(GraphmlType.class);
		}
		catch(Exception e){
			throw new GraphmlClientException("error getting graph graphname="+graphname, e);
		}

		return graphmlType;
	}









}