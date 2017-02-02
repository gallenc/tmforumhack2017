package org.openoss.karaf.features.tmforum.spm.api.event.service;

import javax.management.Notification;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;



public interface ServiceProblemEventListenerService {

	/**
	 * publish {EventTYPE} POST /listener 
	 * 
	 * @return
	 */

	public StatusMessage publishEvent( Notification notification);
}
