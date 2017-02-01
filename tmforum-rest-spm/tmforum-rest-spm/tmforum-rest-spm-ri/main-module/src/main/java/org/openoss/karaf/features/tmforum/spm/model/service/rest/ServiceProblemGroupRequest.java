package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemGroupRequest {

	ServiceProblem parentproblem=null;
	
	List<ServiceProblem> childproblems=null;
	
	public ServiceProblem getParentproblem() {
		return parentproblem;
	}

	@XmlElement(name = "parentproblem")
	public void setParentproblem(ServiceProblem parentproblem) {
		this.parentproblem = parentproblem;
	}
	
	public List<ServiceProblem> getChildproblems() {
		return childproblems;
	}

    @XmlElement(name = "childproblems")
	public void setProblems(List<ServiceProblem> childproblems) {
		this.childproblems = childproblems;
	}


	
}
