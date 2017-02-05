package org.openoss.karaf.features.tmforum.spm.api.event.service;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.openoss.karaf.features.tmforum.spm.api.service.Reply;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;


public interface ServiceProblemEventPublisherService {

	/**
	 * REGISTER LISTENER POST /hub 
	 * 
	 * @return
	 */
	public 	Reply registerListener(MultivaluedMap<String, String> queryParams, List<String> fields);
	
	
	/**
	 * UNREGISTER LISTENER DELETE hub/{id}
	 * 
	 * @return
	 */
	public Reply unregisterListener(String id);
	
}
