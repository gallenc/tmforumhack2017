package org.openoss.karaf.features.tmforum.spm.api.event.service;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.openoss.karaf.features.tmforum.spm.api.service.ErrorMessage;


public interface ServiceProblemEventPublisherService {

	/**
	 * REGISTER LISTENER POST /hub 
	 * 
	 * @return
	 */
	public 	ErrorMessage registerListener(MultivaluedMap<String, String> queryParams, List<String> fields);
	
	
	/**
	 * UNREGISTER LISTENER DELETE hub/{id}
	 * 
	 * @return
	 */
	public ErrorMessage unregisterListener(String id);
	
}
