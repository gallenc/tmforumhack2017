package org.openoss.karaf.features.tmforum.spm.api.service;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemEventRecordsResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemsResponse;
import org.openoss.karaf.features.tmforum.spm.api.service.ErrorMessage;


public interface ServiceProblemService {

	/**
	 * GET /api/serviceProblem/{id}
	 * 
	 * Query Entities. GET must be used to retrieve a representation of a resource.
	
	 * @param 
	 * @return
	 */
	ServiceProblemsResponse getServiceProblem(String id, List<String> fields);

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
	ServiceProblemsResponse getServiceProblems(MultivaluedMap<String, String> queryParams, List<String> fields, Integer lowRange, Integer hiRange);

	/**
	 * POST api/serviceProblem/
	 * 
	 * Create Entity POST must be used to create a new resource
	 * 
	 * @param serviceProblem
	 *            )
	 * @return
	 */
	ErrorMessage postServiceProblem(ServiceProblem serviceProblem);

	/**
	 * PUT api/serviceProblem/{ID}
	 * Complete Update of an Entity PUT must be used to completely update a resource identified by its resource URI
	 * 
	 * @param id
	 * @return
	 */
	ErrorMessage putServiceProblem(String id, ServiceProblem serviceProblem);

	/**
	 * DELETE api/serviceProblem/{ID}
	 * 
	 * Remove an Entity DELETE must be used to remove a resource
	 * 
	 * @param id
	 * @return
	 */
	ErrorMessage deleteServiceProblem(String id);

	/**
	 * POST api/serviceProblem/ack
	 * 
	 * Execute an Action on an Entity POST must be used to execute Task Resources
	 * 
	 * @param id
	 * @return
	 */
	ServiceProblemAckResponse ackServiceProblem(ServiceProblemAckRequest serviceProblemAckRequest);

	/**
	 * POST api/serviceProblem/unack
	 * 
	 * @param id
	 * @return
	 */
	ServiceProblemUnAckResponse unackServiceProblem(
			ServiceProblemUnAckRequest serviceProblemUnAckRequest);

	/**
	 * POST api/serviceProblem/group
	 * 
	 * @param id
	 * @return
	 */
	ServiceProblemGroupResponse groupServiceProblem(ServiceProblemGroupRequest groupRequest);

	/**
	 * POST api/serviceProblem/ungroup
	 * 
	 * @param id
	 * @return
	 */
	ServiceProblemUngroupResponse ungroupServiceProblem(ServiceProblemUngroupRequest unGroupRequest);

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord?{filter}&{attributeSelector}
	 * 
	 * see example
	 * https://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/
	 * String fields = info.getQueryParameters().getFirst("fields");
	 * parameter selector comma seperated list
	 * List<String> ids = info.getQueryParameters().get("id");
	 */
	ServiceProblemEventRecordsResponse getServiceProblemEventRecords(MultivaluedMap<String, String> queryParams, List<String> fields, Integer lowRange, Integer hiRange);

	/**
	 * GET /api/serviceProblem/serviceProblemEventRecord/{id}
	 * 
	 */
	ServiceProblemEventRecordsResponse getServiceProblemEventRecord(String id);

}