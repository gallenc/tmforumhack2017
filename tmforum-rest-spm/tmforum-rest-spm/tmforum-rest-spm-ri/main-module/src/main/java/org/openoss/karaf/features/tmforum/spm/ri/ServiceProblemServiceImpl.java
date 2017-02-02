package org.openoss.karaf.features.tmforum.spm.ri;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.openoss.karaf.features.tmforum.spm.api.service.ServiceProblemService;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
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

public class ServiceProblemServiceImpl implements ServiceProblemService {

	@Override
	public ServiceProblemsResponse getServiceProblem(String id,
			List<String> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemsResponse getServiceProblems(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessage postServiceProblem(ServiceProblem serviceProblem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessage putServiceProblem(String id,
			ServiceProblem serviceProblem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusMessage deleteServiceProblem(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemAckResponse ackServiceProblem(
			ServiceProblemAckRequest serviceProblemAckRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemUnAckResponse unackServiceProblem(
			ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemGroupResponse groupServiceProblem(
			ServiceProblemGroupRequest groupRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemUngroupResponse ungroupServiceProblem(
			ServiceProblemUngroupRequest unGroupRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecords(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecord(
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
