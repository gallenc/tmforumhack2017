package org.openoss.karaf.features.tmforum.spm.api.event.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



public interface ServiceProblemEventListenerRestService {

	/**
	 * publish {EventTYPE} POST /listener 
	 * 
	 * @return
	 */
	@POST
	@Path("/listener")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response publishEvent(@QueryParam("id") String id);
}
