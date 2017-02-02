package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemGroupResponse {

	List<ServiceProblem> problems=null;

	public List<ServiceProblem> getProblems() {
		return problems;
	}

    @XmlElement(name = "problems")
	public void setProblems(List<ServiceProblem> problems) {
		this.problems = problems;
	}
    
	// NOT IN STANDARD
	StatusMessage reportedError=null;
	

	public StatusMessage getReportedError() {
		return reportedError;
	}

	@XmlElement()
	public void setReportedError(StatusMessage reportedError) {
		this.reportedError = reportedError;
	}
	
}
