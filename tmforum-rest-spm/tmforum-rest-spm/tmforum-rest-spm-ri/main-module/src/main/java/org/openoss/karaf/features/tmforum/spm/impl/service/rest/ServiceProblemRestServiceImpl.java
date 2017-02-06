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

package org.openoss.karaf.features.tmforum.spm.impl.service.rest;

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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.openoss.karaf.features.tmforum.spm.api.service.Reply;
import org.openoss.karaf.features.tmforum.spm.api.service.ServiceProblemService;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblemEventRecord;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemRestService;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/spm")
public class ServiceProblemRestServiceImpl implements ServiceProblemRestService {
	// note the class loader cannot find this logger so using ServiceLoader log instead
	// private static final Logger LOG = LoggerFactory.getLogger(ServiceProblemRestServiceImpl.class);

	// curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/spm/api/serviceProblem/10

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#getServiceProblem(java.lang.String)
	 */
	@GET
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblem(@PathParam("id") String id){
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");
		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+" getServiceProblem id="+id);
		try {
			List<String> fields=null; //TODO FIELDS
			ServiceProblemResponse serviceProblemResponse = serviceProblemService.getServiceProblem(id, fields);
			StatusMessage statusMessage = serviceProblemResponse.getStatusMessage();
			if (! ServiceLoader.getErrorReply()){
				serviceProblemResponse.setStatusMessage(null);
			}
			return Response.status(statusMessage.getStatus()).entity(serviceProblemResponse).build();
		} catch (Exception exception){
			//return status 500 Error
			if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+" getServiceProblem exception",exception);
			StatusMessage statusMessage=new StatusMessage(Status.INTERNAL_SERVER_ERROR, 0, "internal error in getServiceProblem", null, exception);
			Reply reply= new ServiceProblemResponse();
			reply.setStatusMessage(statusMessage);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(reply).build();
		}

	}

	// curl -H Accept:application/json http://localhost:8181/serviceProblemManagement/rest/v1-0/spm/api/serviceProblem/?id=10

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#getServiceProblems(javax.ws.rs.core.UriInfo, java.lang.String)
	 */
	@GET
	@Path("/api/serviceProblem/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response getServiceProblems(@Context UriInfo info, @HeaderParam("Range") String range) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"getServiceProblems range="+range+" requestUri="+info.getRequestUri());

		//MultivaluedMap<String, String> queryParams = info.getQueryParameters();



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

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#postServiceProblem(org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem)
	 */
	@POST
	@Path("/api/serviceProblem/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response postServiceProblem(ServiceProblem serviceProblem) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"postServiceProblem called");
		// TODO Auto-generated method stub
		// change id
		ServiceProblem newServiceProblem = serviceProblem;

		return Response.status(201).entity(newServiceProblem).build();
	}



	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#putServiceProblem(java.lang.String, org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem)
	 */
	@PUT
	@Path("/api/serviceProblem/")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response putServiceProblem(String id, ServiceProblem serviceProblem) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"putServiceProblem id="+id);
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

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#deleteServiceProblem(java.lang.String)
	 */
	@DELETE
	@Path("/api/serviceProblem/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response deleteServiceProblem(@PathParam("id") String id){
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"deleteServiceProblem called for id="+id);
		// TODO Auto-generated method stub
		return Response.status(200).build();
	}

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#ackServiceProblem(org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckRequest)
	 */
	@POST
	@Path("/api/serviceProblem/ack")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response ackServiceProblem(ServiceProblemAckRequest serviceProblemAckRequest) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"ackServiceProblem called");

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


	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#unackServiceProblem(org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckRequest)
	 */
	@POST
	@Path("/api/serviceProblem/unack")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response unackServiceProblem(ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"unackServiceProblem called");
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

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#groupServiceProblem(org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupRequest)
	 */
	@POST
	@Path("/api/serviceProblem/group")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response groupServiceProblem(ServiceProblemGroupRequest groupRequest) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"groupServiceProblem called");
		// TODO Auto-generated method stub

		ServiceProblemGroupResponse serviceProblemGroupResponse = new ServiceProblemGroupResponse();
		return Response.status(200).entity(serviceProblemGroupResponse).build();
	}

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#ungroupServiceProblem(org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupRequest)
	 */
	@POST
	@Path("/api/serviceProblem/ungroup")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response ungroupServiceProblem(ServiceProblemUngroupRequest unGroupRequest) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		ServiceLoader.getLog().debug("ungroupServiceProblem called");
		// TODO Auto-generated method stub
		ServiceProblemUngroupResponse serviceProblemUngroupResponse = new ServiceProblemUngroupResponse();
		return Response.status(200).entity(serviceProblemUngroupResponse).build();

	}

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#getServiceProblemEventRecords(javax.ws.rs.core.UriInfo, java.lang.String)
	 */
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Override
	public Response getServiceProblemEventRecords(@Context UriInfo info, @HeaderParam("Range") String range) {
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"getServiceProblemEventRecords range="+range+" requestUri="+info.getRequestUri());
		// TODO Auto-generated method stub
		ServiceProblemEventRecord sper = new ServiceProblemEventRecord();
		List<ServiceProblemEventRecord> sperlist= new ArrayList<ServiceProblemEventRecord>();
		sperlist.add(sper);
		return Response.status(200).entity(sper).build();
	}

	/* (non-Javadoc)
	 * @see org.openoss.karaf.features.tmforum.spm.ri.ServiceProblemService#getServiceProblemEventRecord(java.lang.String)
	 */
	@GET
	@Path("/api/serviceProblem/serviceProblemEventRecord/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getServiceProblemEventRecord(@PathParam("id") String id){
		ServiceProblemService serviceProblemService = ServiceLoader.getServiceProblemService();
		if (serviceProblemService==null) throw new RuntimeException("ServiceLoader.getServiceProblemService() cannot be null.");

		Logger logger = ServiceLoader.getLog();
		if (logger.isDebugEnabled()) logger.debug(this.getClass().getSimpleName()+"getServiceProblemEventRecord called");
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		try{
	//			if (licenceSpec == null) throw new RuntimeException("licenceSpec cannot be null.");
	//			licencePublisher.addLicenceSpec(licenceSpec);
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to add licence specification", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		Boolean removed = null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			removed = licencePublisher.removeLicenceSpec(productId);
	//			String devMessage=null;
	//			if (!removed) return Response.status(400).entity(new StatusMessage(400, 0, "Licence Specification not found to remove for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to remove licence specification", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		LicenceSpecification licenceSpec=null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			licenceSpec = licencePublisher.getLicenceSpec(productId);
	//			String devMessage=null;
	//			if (licenceSpec==null) return Response.status(400).entity(new StatusMessage(400, 0, "Licence Specification not found for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to get licence specification", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		LicenceSpecification licenceSpec=null;
	//		try{
	//			if (productId == null) throw new RuntimeException("productId cannot be null.");
	//			licenceSpec = licencePublisher.getLicenceSpec(productId);
	//			String devMessage=null;
	//			if (licenceSpec==null) return Response.status(400).entity(new StatusMessage(400, 0, "Licence Metadata not found for productId="+productId, null, devMessage)).build();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to get Licence Metadata", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		Map<String, LicenceSpecification> lcnceSpecMap=null;
	//		try{
	//			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		Map<String, LicenceSpecification> lcnceSpecMap=null;
	//		try{
	//			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		try{
	//			if (!"true".equals(confirm)) throw new IllegalArgumentException("Will only delete specs if paramater confirm=true");
	//			licencePublisher.deleteLicenceSpecifications();
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to delete licence specifications", null, exception)).build();
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
	//		LicencePublisher licencePublisher= ServiceLoaderOld.getLicencePublisher();
	//		if (licencePublisher == null) throw new RuntimeException("ServiceLoaderOld.getLicencePublisher() cannot be null.");
	//
	//		String licenceInstanceStr=null;
	//		try{
	//			if (licenceMetadata == null) throw new RuntimeException("licenceMetadata cannot be null.");
	//			licenceInstanceStr = licencePublisher.createLicenceInstanceStr(licenceMetadata);
	//		} catch (Exception exception){
	//			//return status 400 Error
	//			return Response.status(400).entity(new StatusMessage(400, 0, "Unable to create licence instance", null, exception)).build();
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
