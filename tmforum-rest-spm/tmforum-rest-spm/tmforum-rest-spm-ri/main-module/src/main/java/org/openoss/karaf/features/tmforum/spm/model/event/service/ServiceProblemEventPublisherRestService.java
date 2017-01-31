package org.openoss.karaf.features.tmforum.spm.model.event.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public interface ServiceProblemEventPublisherRestService {

	/**
	 * REGISTER LISTENER POST /hub 
	 * 
	 * @return
	 */
	@POST
	@Path("/hub")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerListener();
	
	
	/**
	 * UNREGISTER LISTENER DELETE hub/{id}
	 * 
	 * @return
	 */
	@DELETE
	@Path("/hub")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unregisterListener(@QueryParam("id") String id);
	
}
