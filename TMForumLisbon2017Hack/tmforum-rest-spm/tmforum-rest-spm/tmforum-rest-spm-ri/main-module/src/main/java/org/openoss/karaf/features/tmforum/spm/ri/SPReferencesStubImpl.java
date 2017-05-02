package org.openoss.karaf.features.tmforum.spm.ri;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openoss.karaf.features.tmforum.spm.model.entity.LocationRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.OriginatorPartyRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.RelatedObjectRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.ResourceAlarmRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.ResourceRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.ResponsiblePartyRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.SLAviolationRef;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TroubleTicketRef;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a stub entity service for referencing the following resources
 * OriginatorPartyRef
 * RelatedObjectRef
 * RelatedServiceProblemRef  (not included here)
 * ResourceAlarmRef
 * ResourceRef
 * ResponsiblePartyRef
 * SLAviolationRef
 * TroubleTicketRef
 * LocationRef
 * 
 * @author admin
 *
 */
@Path("/stub")
public class SPReferencesStubImpl {

	private static final Logger LOG = LoggerFactory.getLogger(SPReferencesStubImpl.class);

	// 

	/**
	 * GET /api/originatorPartyRef/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/originatorPartyRef/10
	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/originatorPartyRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOriginatorPartyRef(@PathParam("id") String id){
		LOG.debug("getOriginatorPartyRef id="+id);

		OriginatorPartyRef sp = new OriginatorPartyRef();
		sp.setId(id);
		sp.setHref("OriginatorPartyRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}


	// 

	/**
	 * GET /api/relatedObjectRef/{id}
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/relatedObjectRef/10
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/relatedObjectRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getRelatedObjectRef(@PathParam("id") String id){
		LOG.debug("getRelatedObjectRef id="+id);

		RelatedObjectRef sp = new RelatedObjectRef();
		sp.setId(id);
		sp.setHref("relatedObjectRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}
	



	/**
	 * GET /api/resourceAlarmRef/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/resourceAlarmRef/10
	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/resourceAlarmRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getResourceAlarmRef(@PathParam("id") String id){
		LOG.debug("getResourceAlarmRef id="+id);

		ResourceAlarmRef sp = new ResourceAlarmRef();
		sp.setId(id);
		sp.setHref("resourceAlarmRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}
	
	/**
	 * GET /api/resourceRef/{id}
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/resourceRef/10
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/resourceRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getResourceRef(@PathParam("id") String id){
		LOG.debug("getResourceRef id="+id);

		ResourceRef sp = new ResourceRef();
		sp.setId(id);
		sp.setHref("ResourceRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}

	/**
	 * GET /api/responsiblePartyRef/{id}
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/responsiblePartyRef/10
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/ResponsiblePartyRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getResponsiblePartyRef(@PathParam("id") String id){
		LOG.debug("getResponsiblePartyRef id="+id);

		ResponsiblePartyRef sp = new ResponsiblePartyRef();
		sp.setId(id);
		sp.setHref("ResponsiblePartyRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}
	
	/**
	 * GET /api/SLAviolationRef/{id}
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/SLAviolationRef/10
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/SLAviolationRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getSLAviolationRef(@PathParam("id") String id){
		LOG.debug("getSLAviolationRef id="+id);

		SLAviolationRef sp = new SLAviolationRef();
		sp.setId(id);
		sp.setHref("SLAviolationRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}

	/**
	 * GET /api/troubleTicketRef/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/troubleTicketRef/10

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/TroubleTicketRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getTroubleTicketRef(@PathParam("id") String id){
		LOG.debug("getTroubleTicketRef id="+id);

		TroubleTicketRef sp = new TroubleTicketRef();
		sp.setId(id);
		sp.setHref("troubleTicketRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}
	
	/**
	 * GET /api/locationRef/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	 * 
	 * try curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/stub/api/locationRef/10

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/locationRef/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getLocationRef(@PathParam("id") String id){
		LOG.debug("getTroubleTicketRef id="+id);

		LocationRef sp = new LocationRef();
		sp.setId(id);
		sp.setHref("troubleTicketRef/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}
	


}