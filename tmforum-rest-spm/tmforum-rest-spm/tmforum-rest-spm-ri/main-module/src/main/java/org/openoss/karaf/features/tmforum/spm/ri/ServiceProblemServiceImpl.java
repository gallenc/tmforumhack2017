package org.openoss.karaf.features.tmforum.spm.ri;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.openoss.karaf.features.tmforum.spm.api.service.ServiceProblemService;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemEventRecordsResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemGroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUnAckResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupRequest;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemUngroupResponse;
import org.openoss.karaf.features.tmforum.spm.model.service.rest.ServiceProblemsResponse;

public class ServiceProblemServiceImpl implements ServiceProblemService {

	@Override
	public ServiceProblemResponse getServiceProblem(String id,
			List<String> fields) {

		ServiceProblem problem = new ServiceProblem();
		problem.setId(id);
		problem.setHref("serviceProblem/"+id);
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(problem);

		StatusMessage statusMessage=
				new StatusMessage(Status.OK);
		serviceProblemResponse.setStatusMessage(statusMessage);

		return serviceProblemResponse;

	}

	@Override
	public ServiceProblemsResponse getServiceProblems(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {

		throw new  RuntimeException("getServiceProblems not implimented");
	}

	@Override
	public StatusMessage postServiceProblem(ServiceProblem serviceProblem) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("postServiceProblem not implimented");
	}

	@Override
	public StatusMessage putServiceProblem(String id,
			ServiceProblem serviceProblem) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("putServiceProblems not implimented");
	}

	@Override
	public StatusMessage deleteServiceProblem(String id) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("deleteServiceProblems not implimented");
	}

	@Override
	public ServiceProblemAckResponse ackServiceProblem(
			ServiceProblemAckRequest serviceProblemAckRequest) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("ackServiceProblems not implimented");
	}

	@Override
	public ServiceProblemUnAckResponse unackServiceProblem(
			ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("unackServiceProblems not implimented");
	}

	@Override
	public ServiceProblemGroupResponse groupServiceProblem(
			ServiceProblemGroupRequest groupRequest) {
		// TODO
		throw new  RuntimeException("groupServiceProblems not implimented");
	}

	@Override
	public ServiceProblemUngroupResponse ungroupServiceProblem(
			ServiceProblemUngroupRequest unGroupRequest) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("UNGROUPServiceProblems not implimented");
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecords(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecords not implimented");
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecord(
			String id) {
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecord not implimented");
	}

}
