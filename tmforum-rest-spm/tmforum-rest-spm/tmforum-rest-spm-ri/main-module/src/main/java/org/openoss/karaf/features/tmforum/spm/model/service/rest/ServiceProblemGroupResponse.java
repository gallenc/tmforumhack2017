package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.api.service.Reply;
import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemGroupResponse implements Reply {

	List<ServiceProblem> problems=null;

	public List<ServiceProblem> getProblems() {
		return problems;
	}

    @XmlElement(name = "problems")
	public void setProblems(List<ServiceProblem> problems) {
		this.problems = problems;
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
