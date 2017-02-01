package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.api.service.ErrorMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemUngroupResponse {

	List<ServiceProblem> problems=null;

	public List<ServiceProblem> getProblems() {
		return problems;
	}

    @XmlElement(name = "problems")
	public void setProblems(List<ServiceProblem> problems) {
		this.problems = problems;
	}
    
	// NOT IN STANDARD
	ErrorMessage reportedError=null;
	

	public ErrorMessage getReportedError() {
		return reportedError;
	}

	@XmlElement()
	public void setReportedError(ErrorMessage reportedError) {
		this.reportedError = reportedError;
	}
	
}
