package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.*;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;

@Api(value="/serviceProblem", description = "Service Problem Management Api")
public interface ServiceProblemRestService {

	/**
	 * GET /api/serviceProblem/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@ApiOperation(value = "GET ServiceProblem",
			notes = "request for service problem",
			position = 1)
	@GET
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblem(@ApiParam(value = "id of serviceProblem to return") @PathParam("id") String id);

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
	@ApiOperation(value = "GET ServiceProblems",
			notes = "query request for service problems",
			position = 2)
	@GET
	@Path("/api/serviceProblem")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblems(@Context UriInfo info, @HeaderParam("Range") String range);

	/**
	 * POST api/serviceProblem/
	 * 
	 * Create Entity POST must be used to create a new resource
	 * 
	 * @param serviceProblem
	 *            )
	 * @return
	 */
	@ApiOperation(value = "POST ServiceProblem",
			notes = "create new service problem",
			position = 3)
	@POST
	@Path("/api/serviceProblem/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response postServiceProblem(@ApiParam(value = "service problem") ServiceProblem serviceProblem);

	/**
	 * PUT api/serviceProblem/{ID}
	 * Complete Update of an Entity PUT must be used to completely update a resource identified by its resource URI
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "PUT ServiceProblems",
			notes = "update service problem",
			position = 4)
	@PUT
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response putServiceProblem(@ApiParam(value = "id of serviceProblem to return") @PathParam("id") String id,  ServiceProblem serviceProblem);

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
	//	@Path("/api/serviceProblem/{id}")
	//	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//	public Response patchServiceProblem(@PathParam("id") String id,  ServiceProblem serviceProblem);

	/**
	 * DELETE api/serviceProblem/{ID}
	 * 
	 * Remove an Entity DELETE must be used to remove a resource
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "DELETE ServiceProblem",
			notes = "delete service problem identified by id",
			position = 6)
	@DELETE
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

	public Response deleteServiceProblem(@ApiParam(value = "id of serviceProblem to return") @PathParam("id") String id);

	/**
	 * POST api/serviceProblem/ack
	 * 
	 * Execute an Action on an Entity POST must be used to execute Task Resources
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Acknowledge ServiceProblems",
			notes = "acknowledge a list of service problems",
			position = 6)
	@POST
	@Path("/api/serviceProblem/ack")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response ackServiceProblem(ServiceProblemAckRequest serviceProblemAckRequest);

	/**
	 * POST api/serviceProblem/unack
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Unacknowledge ServiceProblems",
			notes = "unacknowledge a list of service problems",
			position = 7)
	@POST
	@Path("/api/serviceProblem/unack")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response unackServiceProblem(ServiceProblemUnAckRequest serviceProblemUnAckRequest);

	/**
	 * POST api/serviceProblem/group
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Group (correlate) ServiceProblems",
			notes = "correlate service problems to a parent service problem problems",
			position = 8)
	@POST
	@Path("/api/serviceProblem/group")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response groupServiceProblem(ServiceProblemGroupRequest groupRequest);

	/**
	 * POST api/serviceProblem/ungroup
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Ungroup (correlate) ServiceProblems",
			notes = "correlate service problems to a parent service problem problems",
			position = 9)
	@POST
	@Path("/api/serviceProblem/ungroup")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response ungroupServiceProblem(ServiceProblemUngroupRequest ungroupRequest);

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord?{filter}&{attributeSelector}
	 * 
	 * see example
	 * https://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * String fields = info.getQueryParameters().getFirst("fields");
	 * parameter selector comma seperated list
	 * List<String> ids = info.getQueryParameters().get("id");
	 */
	@ApiOperation(value = "get ServiceProblemEventRecord",
			notes = "query for a list of ServiceProblemEventRecords",
			position = 10)
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblemEventRecords(@Context UriInfo info, @HeaderParam("Range") String range);

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord/{id}
	 * 
	 */
	@ApiOperation(value = "get ServiceProblemEventRecord",
			notes = "retrieve a given ServiceProblemEventRecord",
			position = 11)
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblemEventRecord(@ApiParam(value = "id of ServiceProblemEventRecord to return") @PathParam("id") String id);



}
