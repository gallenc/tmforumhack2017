package org.openoss.karaf.features.tmforum.spm.model.service.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.api.service.StatusMessage;
import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblemEventRecord;



@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ServiceProblemEventRecordsResponse {

	List<ServiceProblemEventRecord> serviceProblemEventRecord=null;
	
	public List<ServiceProblemEventRecord> getServiceProblemEventRecord() {
		return serviceProblemEventRecord;
	}

	@XmlElement(name = "eventRecord")
	public void setServiceProblemEventRecord(
			List<ServiceProblemEventRecord> serviceProblemEventRecord) {
		this.serviceProblemEventRecord = serviceProblemEventRecord;
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
