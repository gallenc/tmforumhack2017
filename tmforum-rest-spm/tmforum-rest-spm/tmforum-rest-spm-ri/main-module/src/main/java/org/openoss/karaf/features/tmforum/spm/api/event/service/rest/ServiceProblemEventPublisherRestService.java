package org.openoss.karaf.features.tmforum.spm.api.event.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


public interface ServiceProblemEventPublisherRestService {

	/**
	 * REGISTER LISTENER POST /hub 
	 * The POST call sets the communication endpoint address 
	 * the service instance must use to deliver notifications 
	 * (by default on all supported events). 
	 * Note that a query expression may be supplied. 
	 * The query expression may be used to filter specific event 
	 * types and/or any content of the event. 
	 * The query expression  structure used for 
	 * notification filtering is the same than the 
	 * one used for queries i.e GET.
	 * 
	 * @return
	 */
	@POST
	@Path("/hub")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerListener(@Context UriInfo info);
	
	
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
