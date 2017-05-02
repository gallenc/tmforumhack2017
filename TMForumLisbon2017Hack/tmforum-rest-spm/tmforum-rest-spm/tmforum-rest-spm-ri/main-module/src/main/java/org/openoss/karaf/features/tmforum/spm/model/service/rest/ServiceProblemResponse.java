package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.api.service.Reply;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;


@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemResponse implements Reply{

	ServiceProblem problem=null;

	public ServiceProblem getProblem() {
		return problem;
	}

	@XmlElement
	public void setProblem(ServiceProblem problem) {
		this.problem = problem;
	}

	// NOT IN STANDARD
	StatusMessage statusMessage=null;

	@Override()
	public StatusMessage getStatusMessage() {
		return statusMessage;
	}

	@XmlElement()
	@Override()
	public void setStatusMessage(StatusMessage statusMessage) {
		this.statusMessage = statusMessage;
	}

	
}
