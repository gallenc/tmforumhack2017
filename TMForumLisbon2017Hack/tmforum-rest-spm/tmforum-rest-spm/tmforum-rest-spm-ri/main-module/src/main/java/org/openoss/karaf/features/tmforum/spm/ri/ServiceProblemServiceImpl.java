package org.openoss.karaf.features.tmforum.spm.ri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

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
	
	AtomicInteger serviceProblemId = new AtomicInteger(0);
		
	SortedMap<String, ServiceProblem> serviceProblemList = Collections.synchronizedSortedMap(new TreeMap<String,ServiceProblem>());

	@Override
	public synchronized ServiceProblemResponse getServiceProblem(String id,	List<String> fields) {
		LOG.debug("getServiceProblem called for id="+id);
//		ServiceProblem problem = new ServiceProblem();
//		problem.setId(id);
//		problem.setHref("serviceProblem/"+id);
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		
		ServiceProblem problem = serviceProblemList.get(id);
		if(problem==null){
			StatusMessage statusMessage= new StatusMessage(Status.NOT_FOUND);
			statusMessage.setDeveloperMessage("ServiceProblem not found with id="+id);
			serviceProblemResponse.setStatusMessage(statusMessage);
			LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
			return serviceProblemResponse;
		}
		serviceProblemResponse.setProblem(problem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);

		return serviceProblemResponse;

	}

	@Override
	public synchronized  ServiceProblemsResponse getServiceProblems(
			MultivaluedMap<String, String> queryParams, List<String> fields, Integer lowRange, Integer hiRange) {
		LOG.debug("getServiceProblems implementation called");

//		ServiceProblem sp = new ServiceProblem();
//		sp.setId(Integer.toString(10));
//		sp.setHref("serviceProblem/"+10);
//		ServiceProblem sp2 = new ServiceProblem();
//		sp2.setId(Integer.toString(11));
//		sp2.setHref("serviceProblem/"+11);
//
//		List<ServiceProblem> splist= new ArrayList<ServiceProblem>();
//		splist.add(sp);
//		splist.add(sp2);
		
		List<ServiceProblem> splist = new ArrayList<ServiceProblem>( serviceProblemList.values());

		ServiceProblemsResponse serviceProblemsResponse= new ServiceProblemsResponse();
		serviceProblemsResponse.setProblems(splist);
		
		StatusMessage statusMessage= new StatusMessage(Status.OK);
		serviceProblemsResponse.setStatusMessage(statusMessage);
		
		return serviceProblemsResponse;
	}

	@Override
	public synchronized  ServiceProblemResponse postServiceProblem(ServiceProblem serviceProblem) {
		LOG.debug("postServiceProblem called for serviceproblem="+serviceProblem);
		
		String id =  Integer.toString(serviceProblemId.addAndGet(1));
		serviceProblem.setId(id);
		
		String href= "http://localhost:8181/serviceProblemManagement/rest/v1-0/spm/api/serviceProblem/"+id;
		serviceProblem.setHref(href);
		
		if(serviceProblem.getTimeRaised()==null){
			serviceProblem.setTimeRaised(new Date());
		}
		
		serviceProblemList.put(id, serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}

	@Override
	public synchronized  ServiceProblemResponse putServiceProblem(String id, ServiceProblem serviceProblem) {
		LOG.debug("putServiceProblem called for id="+id+" serviceproblem="+serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		ServiceProblem problem = serviceProblemList.get(id);
		if(problem==null){
			StatusMessage statusMessage= new StatusMessage(Status.NOT_FOUND);
			statusMessage.setDeveloperMessage("ServiceProblem not found with id="+id);
			serviceProblemResponse.setStatusMessage(statusMessage);
			LOG.debug("putServiceProblem response status="+statusMessage.getStatus());
			return serviceProblemResponse;
		}
		
		serviceProblem.setId(id); //TODO
		
		if(serviceProblem.getTimechanged()==null){
			serviceProblem.setTimechanged(new Date());
		}
		
		serviceProblemList.put(id, serviceProblem);
		

		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}
	
	@Override
	public synchronized  ServiceProblemResponse patchServiceProblem(String id, ServiceProblem serviceProblem) {
		LOG.debug("patchServiceProblem called for id="+id+" serviceproblem="+serviceProblem);
		
		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();
		ServiceProblem problem = serviceProblemList.get(id);
		if(problem==null){
			StatusMessage statusMessage= new StatusMessage(Status.NOT_FOUND);
			statusMessage.setDeveloperMessage("ServiceProblem not found with id="+id);
			serviceProblemResponse.setStatusMessage(statusMessage);
			LOG.debug("putServiceProblem response status="+statusMessage.getStatus());
			return serviceProblemResponse;
		}
		
		serviceProblem.setId(id); //TODO
		
		if(serviceProblem.getTimechanged()==null){
			serviceProblem.setTimechanged(new Date());
		}
		
		serviceProblemList.put(id, serviceProblem); //todo patch
		
		serviceProblemResponse.setProblem(serviceProblem);

		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("getServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;
	}

	@Override
	public synchronized  Reply deleteServiceProblem(String id) {
		LOG.debug("deleteServiceProblem called for id="+id);
		
		ServiceProblem problem = serviceProblemList.remove(id);

		ServiceProblemResponse serviceProblemResponse=new ServiceProblemResponse();

		if(problem==null){
			StatusMessage statusMessage= new StatusMessage(Status.NOT_FOUND);
			statusMessage.setDeveloperMessage("ServiceProblem could not be deleted (not found) with id="+id);
			serviceProblemResponse.setStatusMessage(statusMessage);
			LOG.debug("deleteServiceProblem response status="+statusMessage.getStatus());
			return serviceProblemResponse;
		}
		
		StatusMessage statusMessage= new StatusMessage(Status.OK);
		LOG.debug("deleteServiceProblem response status="+statusMessage.getStatus());
		serviceProblemResponse.setStatusMessage(statusMessage);
		return serviceProblemResponse;

	}

	@Override
	public synchronized  ServiceProblemAckResponse ackServiceProblem(
			ServiceProblemAckRequest serviceProblemAckRequest) {
		LOG.debug("ackServiceProblem called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("ackServiceProblem not implimented");
	}

	@Override
	public synchronized  ServiceProblemUnAckResponse unackServiceProblem(
			ServiceProblemUnAckRequest serviceProblemUnAckRequest) {
		LOG.debug("unackServiceProblems called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("unackServiceProblem not implimented");
	}

	@Override
	public synchronized  ServiceProblemGroupResponse groupServiceProblem(
			ServiceProblemGroupRequest groupRequest) {
		LOG.debug("groupServiceProblem called");
		// TODO
		throw new  RuntimeException("groupServiceProblem not implimented");
	}

	@Override
	public synchronized  ServiceProblemUngroupResponse ungroupServiceProblem(
			ServiceProblemUngroupRequest unGroupRequest) {
		LOG.debug("ungroupServiceProblems called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("ungroupServiceProblem not implimented");
	}

	@Override
	public synchronized  ServiceProblemEventRecordsResponse getServiceProblemEventRecords(
			MultivaluedMap<String, String> queryParams, List<String> fields,
			Integer lowRange, Integer hiRange) {
		LOG.debug("getServiceProblemEventRecords called");
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecords not implimented");
	}

	@Override
	public synchronized  ServiceProblemEventRecordsResponse getServiceProblemEventRecord(
			String id) {
		LOG.debug("getServiceProblemEventRecord called for id="+id);
		// TODO Auto-generated method stub
		throw new  RuntimeException("getServiceProblemEventRecord not implimented");
	}

}
