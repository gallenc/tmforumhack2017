package org.openoss.karaf.features.tmforum.spm.ri;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.openoss.karaf.features.tmforum.spm.api.service.Reply;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProblemServiceImpl implements ServiceProblemService {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceProblemServiceImpl.class);

	@Override
	public ServiceProblemResponse getServiceProblem(String id,	List<String> fields) {
		LOG.debug("getServiceProblem called for id="+id);
		ServiceProblem problem = new ServiceProblem();
		problem.setId(id);
		problem.setHref("serviceProblem/"+id);
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(problem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);

		return serviceProblemResponse;

	}

	@Override
	public ServiceProblemsResponse getServiceProblems(
			MultivaluedMap<String, String> queryParams, List<String> fields, Integer lowRange, Integer hiRange) {
		LOG.debug("getServiceProblems implementation called");

		ServiceProblem sp = new ServiceProblem();
		sp.setId(Integer.toString(10));
		sp.setHref("serviceProblem/"+10);
		ServiceProblem sp2 = new ServiceProblem();
		sp2.setId(Integer.toString(11));
		sp2.setHref("serviceProblem/"+11);

		List<ServiceProblem> splist= new ArrayList<ServiceProblem>();
		splist.add(sp);
		splist.add(sp2);


		ServiceProblemsResponse serviceProblemsResponse= new ServiceProblemsResponse();
		serviceProblemsResponse.setProblems(splist);
		
		StatusMessage statusMessage= new StatusMessage(Status.OK);
		serviceProblemsResponse.setStatusMessage(statusMessage);
		
		return serviceProblemsResponse;
	}

	@Override
	public ServiceProblemResponse postServiceProblem(ServiceProblem serviceProblem) {
		LOG.debug("postServiceProblem called for serviceproblem="+serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}

	@Override
	public ServiceProblemResponse putServiceProblem(String id, ServiceProblem serviceProblem) {
		LOG.debug("putServiceProblem called for id="+id+" serviceproblem="+serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}
	
	@Override
	public ServiceProblemResponse patchServiceProblem(String id, ServiceProblem serviceProblem) {
		LOG.debug("patchServiceProblem called for id="+id+" serviceproblem="+serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}

	@Override
	public Reply deleteServiceProblem(String id) {
		LOG.debug("deleteServiceProblem called for id="+id);
		// TODO Auto-generated method stub
		throw new  RuntimeException("deleteServiceProblem not implimented");
	}

	@Override
	public ServiceProblemAckResponse ackServiceProblem(
			ServiceProblemAckRequest serviceProblemAckRequest) {
		LOG.debug("ackServiceProblem called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("ackServiceProblem not implimented");
	}

	@Override
	public ServiceProblemUnAckResponse unackServiceProblem(
			ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		LOG.debug("unackServiceProblems called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("unackServiceProblem not implimented");
	}

	@Override
	public ServiceProblemGroupResponse groupServiceProblem(
			ServiceProblemGroupRequest groupRequest) {
		LOG.debug("groupServiceProblem called");
		// TODO
		throw new  RuntimeException("groupServiceProblem not implimented");
	}

	@Override
	public ServiceProblemUngroupResponse ungroupServiceProblem(
			ServiceProblemUngroupRequest unGroupRequest) {
		LOG.debug("ungroupServiceProblems called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("ungroupServiceProblem not implimented");
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecords(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {
		LOG.debug("getServiceProblemEventRecords called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecords not implimented");
	}

	@Override
	public ServiceProblemEventRecordsResponse getServiceProblemEventRecord(
			String id) {
		LOG.debug("getServiceProblemEventRecord called for id="+id);
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecord not implimented");
	}

}
