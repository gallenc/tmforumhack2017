package org.openoss.karaf.features.tmforum.spm.model.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;

public interface ServiceProblemRestService {

	/**
	 * GET /api/serviceProblem/?{filter}&{attributeSelector}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	 * 
	 * see example https://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * String fields = info.getQueryParameters().getFirst("fields");
	 * parameter selector comma seperated list
	 * List<String> ids = info.getQueryParameters().get("id");
	 * @param licence
	 * @return
	 */
	@GET
	@Path("/api/serviceProblem/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceProblem(@Context UriInfo info, @HeaderParam("Range") String range);

	/**
	 * POST api/serviceProblem/
	 * 
	 * Create Entity POST must be used to create a new resource
	 * 
	 * @param serviceProblem
	 *            )
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postServiceProblem(ServiceProblem serviceProblem);

	/**
	 * PUT api/serviceProblem/{ID}
	 * Complete Update of an Entity PUT must be used to completely update a resource identified by its resource URI
	 * 
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/api/serviceProblem/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putServiceProblem(@QueryParam("id") String id, ServiceProblem serviceProblem);

	/**
	 * PATCH api/serviceProblem/{ID}
	 * 
	 * NOTE patch not directly supported by jersey 
	 * http://stackoverflow.com/questions/22355235/patch-request-using-jersey-client
	 * https://github.com/jersey/jersey/tree/master/examples/http-patch
	 * http://stackoverflow.com/questions/17897171/how-to-have-a-patch-annotation-for-jax-rs
	 * 
	 * Partial Update of an Entity PATCH must be used to partially update a resource
	 * 
	 * @param id
	 * @return
	 */
	//@PATCH
//	@Path("/api/serviceProblem/")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response patchServiceProblem(@QueryParam("id") String id, ServiceProblem serviceProblem);

	/**
	 * DELETE api/serviceProblem/{ID}
	 * 
	 * Remove an Entity DELETE must be used to remove a resource
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/api/serviceProblem/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteServiceProblem(@QueryParam("id") String id);

	/**
	 * POST api/serviceProblem/ack
	 * 
	 * Execute an Action on an Entity POST must be used to execute Task Resources
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/ack")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ackServiceProblem(@QueryParam("id") String id);

	/**
	 * POST api/serviceProblem/unack
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/unack")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unackServiceProblem(@QueryParam("id") String id);

	/**
	 * POST api/serviceProblem/group
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/group")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response groupServiceProblem();

	/**
	 * POST api/serviceProblem/ungroup
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/ungroup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ungroupServiceProblem();

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord?{filter}&{attributeSelector}
	 * 
	 * see example
	 * https://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * String fields = info.getQueryParameters().getFirst("fields");
	 * parameter selector comma seperated list
	 * List<String> ids = info.getQueryParameters().get("id");
	 */
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServiceProblemEventRecord(@Context UriInfo info, @HeaderParam("Range") String range);



}
