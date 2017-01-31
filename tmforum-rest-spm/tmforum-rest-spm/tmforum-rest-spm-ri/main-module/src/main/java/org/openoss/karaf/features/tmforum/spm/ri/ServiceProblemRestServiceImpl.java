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

package org.openoss.karaf.features.tmforum.spm.ri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblemEventRecord;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemGroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemGroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemUnAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemUngroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemRestService;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemUnAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemUngroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.ServiceProblemsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/spm")
public class ServiceProblemRestServiceImpl implements ServiceProblemRestService {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceProblemRestServiceImpl.class);

	// curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/spm/api/serviceProblem/10

	/**
	 * GET /api/serviceProblem/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.

	 * @param 
	 * @return
	 */
	@GET
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblem(@PathParam("id") String id){
		LOG.debug("getServiceProblem id="+id);

		ServiceProblem sp = new ServiceProblem();
		sp.setId(id);
		sp.setHref("serviceProblem/"+id);
		// TODO Auto-generated method stub
		return Response.status(200).entity(sp).build();
	}

	// curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/spm/api/serviceProblem/?id=10

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
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response getServiceProblems(@Context UriInfo info, @HeaderParam("Range") String range) {
		LOG.debug("getServiceProblems range="+range+" requestUri="+info.getRequestUri());

		// TODO Auto-generated method stub
		ServiceProblem sp = new ServiceProblem();
		sp.setId(Integer.toString(10));
		sp.setHref("serviceProblem/"+10);
		ServiceProblem sp2 = new ServiceProblem();
		sp2.setId(Integer.toString(11));
		sp2.setHref("serviceProblem/"+11);

		List<ServiceProblem> splist= new ArrayList<ServiceProblem>();
		splist.add(sp);
		splist.add(sp2);


		ServiceProblemsResponse serviceProblemResponse= new ServiceProblemsResponse();
		serviceProblemResponse.setProblems(splist);

		return Response.status(200).entity(serviceProblemResponse).build();

		// see http://stackoverflow.com/questions/27643822/marshal-un-marshal-list-objects-in-jersey-jax-rs-using-jaxb
		// does this work with json?
//		GenericEntity<List<ServiceProblem>> gelist = new GenericEntity<List<ServiceProblem>>(splist) {};
//		return Response.status(200).entity(gelist).build();

		//or try array http://stackoverflow.com/questions/10849526/return-jsonarray-instead-of-jsonobject-jersey-jax-rs
		//ServiceProblem[] sparray= (ServiceProblem[]) splist.toArray();
		//return Response.status(200).entity(sparray).build();
		//		JSONArray arr = new JSONArray();
		//		for (ServiceProblem sprob : splist) {
		//		  arr.put(sprob);
		//		}
		//		
		//		return Response.status(200).entity(arr).build();
	}

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
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response postServiceProblem(ServiceProblem serviceProblem) {
		LOG.debug("postServiceProblem called");
		// TODO Auto-generated method stub
		// change id
		ServiceProblem newServiceProblem = serviceProblem;
		
		return Response.status(201).entity(newServiceProblem).build();
	}



	/**
	 * PUT api/serviceProblem/{ID}
	 * Complete Update of an Entity PUT must be used to completely update a resource identified by its resource URI
	 * 
	 * @param id
	 * @return
	 */
	@PUT
	@Path("/api/serviceProblem/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response putServiceProblem(String id, ServiceProblem serviceProblem) {
		LOG.debug("putServiceProblem id="+id);
		// TODO Auto-generated method stub
		ServiceProblem newServiceProblem = serviceProblem;
		
		return Response.status(201).entity(newServiceProblem).build();
	}



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
	//	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	//	@Override
	//	public Response patchServiceProblem(String id, ServiceProblem serviceProblem) {
	//		LOG.debug("patchServiceProblem called");
	//		// TODO Auto-generated method stub
	//		return Response.status(200).build();
	//	}

	/**
	 * DELETE api/serviceProblem/{ID}
	 * 
	 * Remove an Entity DELETE must be used to remove a resource
	 * 
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response deleteServiceProblem(@PathParam("id") String id){
		LOG.debug("deleteServiceProblem called for id="+id);
		// TODO Auto-generated method stub
		return Response.status(200).build();
	}

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
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response ackServiceProblem(ServiceProblemAckRequest serviceProblemAckRequest) {
		LOG.debug("ackServiceProblem called");

		// TODO Auto-generated method stub
		ServiceProblem sp = new ServiceProblem();
		sp.setId(Integer.toString(10));
		sp.setHref("serviceProblem/"+10);
		ServiceProblem sp2 = new ServiceProblem();
		sp2.setId(Integer.toString(11));
		sp2.setHref("serviceProblem/"+11);

		List<ServiceProblem> splist= new ArrayList<ServiceProblem>();
		splist.add(sp);
		splist.add(sp2);


		ServiceProblemAckResponse serviceProblemAckResponse= new ServiceProblemAckResponse();
		serviceProblemAckResponse.setProblems(splist);

		return Response.status(200).entity(serviceProblemAckResponse).build();
	}


	/**
	 * POST api/serviceProblem/unack
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/unack")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response unackServiceProblem(ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		LOG.debug("unackServiceProblem called");
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ServiceProblem sp = new ServiceProblem();
		sp.setId(Integer.toString(10));
		sp.setHref("serviceProblem/"+10);
		ServiceProblem sp2 = new ServiceProblem();
		sp2.setId(Integer.toString(11));
		sp2.setHref("serviceProblem/"+11);

		List<ServiceProblem> splist= new ArrayList<ServiceProblem>();
		splist.add(sp);
		splist.add(sp2);


		ServiceProblemUnAckResponse serviceProblemUnAckResponse= new ServiceProblemUnAckResponse();
		serviceProblemUnAckResponse.setProblems(splist);

		return Response.status(200).entity(serviceProblemUnAckResponse).build();
	}

	/**
	 * POST api/serviceProblem/group
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/group")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response groupServiceProblem(ServiceProblemGroupRequest groupRequest) {
		LOG.debug("groupServiceProblem called");
		// TODO Auto-generated method stub
		
		ServiceProblemGroupResponse serviceProblemGroupResponse = new ServiceProblemGroupResponse();
		return Response.status(200).entity(serviceProblemGroupResponse).build();
	}

	/**
	 * POST api/serviceProblem/ungroup
	 * 
	 * @param id
	 * @return
	 */
	@POST
	@Path("/api/serviceProblem/ungroup")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response ungroupServiceProblem(ServiceProblemUngroupRequest unGroupRequest) {
		LOG.debug("ungroupServiceProblem called");
		// TODO Auto-generated method stub
		ServiceProblemUngroupResponse serviceProblemUngroupResponse = new ServiceProblemUngroupResponse();
		return Response.status(200).entity(serviceProblemUngroupResponse).build();

	}

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
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response getServiceProblemEventRecords(@Context UriInfo info, @HeaderParam("Range") String range) {
		LOG.debug("getServiceProblemEventRecords range="+range+" requestUri="+info.getRequestUri());
		// TODO Auto-generated method stub
		ServiceProblemEventRecord sper = new ServiceProblemEventRecord();
		List<ServiceProblemEventRecord> sperlist= new ArrayList<ServiceProblemEventRecord>();
		sperlist.add(sper);
		return Response.status(200).entity(sper).build();
	}

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord/{id}
	 * 
	 */
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblemEventRecord(@PathParam("id") String id){
		LOG.debug("getServiceProblemEventRecord called");
		// TODO Auto-generated method stub
		ServiceProblemEventRecord sper = new ServiceProblemEventRecord();
		return Response.status(200).entity(sper).build();
	}

	//
	//	@POST
	//	@Path("/addlicencespec")
	//	@Consumes(MediaType.APPLICATION_XML)
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response addLicenceSpec(LicenceSpecification licenceSpec){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		try{
	//			if (licenceSpec == null) throw new RuntimeException("licenceSpec cannot be null.");
	//			licencePublisher.addLicenceSpec(licenceSpec);
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to add licence specification", null, exception)).build();
	//		}
	//
	//		ReplyMessage reply= new ReplyMessage();
	//		reply.setReplyComment("Licence Specification successfully added");
	//		
	//		return Response
	//				.status(200).entity(reply).build();
	//
	//	}
	//	
	//	
	//	@GET
	//	@Path("/removelicencespec")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response removeLicenceSpec(@QueryParam("productId") String productId){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		Boolean removed = null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			removed = licencePublisher.removeLicenceSpec(productId);
	//			String devMessage=null;
	//			if (!removed) return Response.status(400).entity(new ErrorMessage(400, 0, "Licence Specification not found to remove for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to remove licence specification", null, exception)).build();
	//		}
	//
	//		ReplyMessage reply= new ReplyMessage();
	//		reply.setReplyComment("Licence Specification successfully removed for productId="+productId);
	//		
	//		return Response
	//				.status(200).entity(reply).build();
	//
	//	}
	//
	//	
	//	@GET
	//	@Path("/getlicencespec")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response getLicenceSpec(@QueryParam("productId") String productId){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		LicenceSpecification licenceSpec=null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			licenceSpec = licencePublisher.getLicenceSpec(productId);
	//			String devMessage=null;
	//			if (licenceSpec==null) return Response.status(400).entity(new ErrorMessage(400, 0, "Licence Specification not found for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification", null, exception)).build();
	//		}
	//		
	//		ReplyMessage reply= new ReplyMessage();
	//		reply.setReplyComment("Licence Specification found for productId="+productId);
	//		reply.setLicenceSpecification(licenceSpec);
	//		
	//		return Response.status(200).entity(reply).build();
	//	}
	//	
	//	/**
	//	 * @return licence metadata spec (not the full license spec)
	//	 */
	//	@GET
	//	@Path("/getlicencemetadataspec")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response getLicenceMetadata(@QueryParam("productId") String productId){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		LicenceSpecification licenceSpec=null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			licenceSpec = licencePublisher.getLicenceSpec(productId);
	//			String devMessage=null;
	//			if (licenceSpec==null) return Response.status(400).entity(new ErrorMessage(400, 0, "Licence Metadata not found for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get Licence Metadata", null, exception)).build();
	//		}
	//		
	//		ReplyMessage reply= new ReplyMessage();
	//	    reply.setReplyComment("Licence Metadata Spec found for productId="+productId);
	//		reply.setLicenceMetadataSpec(licenceSpec.getLicenceMetadataSpec());
	//		
	//		return Response.status(200).entity(reply).build();
	//	}
	//	
	//	@GET
	//	@Path("/listspecs")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response getLicenceSpecList(){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		Map<String, LicenceSpecification> lcnceSpecMap=null;
	//		try{
	//			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
	//		}
	//		
	//		LicenceSpecList licenceSpecList= new LicenceSpecList();
	//		licenceSpecList.getLicenceSpecList().addAll(lcnceSpecMap.values());
	//		
	//		return Response
	//				.status(200).entity(licenceSpecList).build();
	//
	//	}
	//
	//	
	//	/**
	//	 * @return list of licence metadata specs (not the full license spec)
	//	 */
	//	@GET
	//	@Path("/list")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response getLicenceMetadataList(){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		Map<String, LicenceSpecification> lcnceSpecMap=null;
	//		try{
	//			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
	//		}
	//		
	//		LicenceMetadataList licenceMetadataList= new LicenceMetadataList();
	//		
	//		Collection<LicenceSpecification> licenceSpecs = lcnceSpecMap.values();
	//		for(LicenceSpecification lspec:licenceSpecs){
	//			LicenceMetadata licenceMetadataSpec = lspec.getLicenceMetadataSpec();
	//			licenceMetadataList.getLicenceMetadataList().add(licenceMetadataSpec);
	//		}
	//		
	//		return Response.status(200).entity(licenceMetadataList).build();
	//
	//	}
	//	
	//	
	//	@GET
	//	@Path("/clearlicencespecs")
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response deleteLicenceSpecifications(@QueryParam("confirm") String confirm){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		try{
	//			if (!"true".equals(confirm)) throw new IllegalArgumentException("Will only delete specs if paramater confirm=true");
	//			licencePublisher.deleteLicenceSpecifications();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to delete licence specifications", null, exception)).build();
	//		}
	//
	//		ReplyMessage reply= new ReplyMessage();
	//        reply.setReplyComment("All Licence Specifications removed");
	//		
	//		return Response.status(200).entity(reply).build();
	//	}
	//	
	//	
	//	@POST
	//	@Path("/createlicence")
	//	@Consumes(MediaType.APPLICATION_XML)
	//	@Produces(MediaType.APPLICATION_XML)
	//	@Override()
	//	public Response createLicenceInstanceStr(LicenceMetadata licenceMetadata){
	//
	//		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");
	//
	//		String licenceInstanceStr=null;
	//		try{
	//			if (licenceMetadata == null) throw new RuntimeException("licenceMetadata cannot be null.");
	//			licenceInstanceStr = licencePublisher.createLicenceInstanceStr(licenceMetadata);
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to create licence instance", null, exception)).build();
	//		}
	//
	//		ReplyMessage reply= new ReplyMessage();
	//        reply.setReplyComment("Successfully created licence instance");
	//        reply.setLicence(licenceInstanceStr);
	//		
	//		return Response.status(200).entity(reply).build();
	//
	//	}

}
