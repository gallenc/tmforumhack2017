package org.openoss.karaf.features.tmforum.spm.model.service;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemUnAckResponse {

	List<ServiceProblem> problems=null;

	public List<ServiceProblem> getProblems() {
		return problems;
	}

    @XmlElement(name = "unackProblems")
	public void setProblems(List<ServiceProblem> problems) {
		this.problems = problems;
	}
	
}
