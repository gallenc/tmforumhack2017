/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opennms.plugins.tmforumapis.rest;

import org.opennms.plugins.tmforumapis.SniffyService;
import org.opennms.plugins.tmforumapis.jaxb.SniffyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 * REST service to retrieve sniffy data
 */
@Path("/measurements")
public class SniffyRestImpl {
	private static final Logger LOG = LoggerFactory.getLogger(SniffyRestImpl.class);

	/**
	 * @return Returns latest Sniffy data (last sent using MQTT)
	 * service will be at http://localhost:8181/tmforumapis/rest/v1-0/measurements/latest-measurement
	 */
	@GET
	@Path("/latest-measurement")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response  getLatestMeasurement() throws Exception {
		LOG.debug("received rest call for /latest-measurement");

		SniffyService sniffyService = ServiceLoader.getSniffyService();
		if (sniffyService == null) throw new RuntimeException("ServiceLoader.getSniffyService() cannot be null.");

		SniffyData sniffyData= sniffyService.getSniffyData();
		if (sniffyData==null){
			return Response.status(500).entity("Internal failure to collect sniffyData").build();  
		} else {
			LOG.debug("rest call for /latest-measurement returning sniffyData= "+sniffyData);
			return Response.status(200).entity(sniffyData).build();  
		}
	}



} 