package org.openoss.karaf.features.tmforum.spm.model.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceProblem {

	private String href = null;
	private String id = null;

	private String correlationID = null;
	private String originatingsystem = null;
	private String category = null;
	private String impactImportanceFactor = null;
	private Integer priority = null;
	private String description = null;
	private String problemEscalation = null;
	private Date timeRaised = null;
	private Date timechanged = null;
	private Date statusChangeDate = null;
	private Date resolutionDate = null;
	private String statusChangeReason = null;
	private String reason = null;
	
	
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getOriginatingsystem() {
		return originatingsystem;
	}
	public void setOriginatingsystem(String originatingsystem) {
		this.originatingsystem = originatingsystem;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImpactImportanceFactor() {
		return impactImportanceFactor;
	}
	public void setImpactImportanceFactor(String impactImportanceFactor) {
		this.impactImportanceFactor = impactImportanceFactor;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProblemEscalation() {
		return problemEscalation;
	}
	public void setProblemEscalation(String problemEscalation) {
		this.problemEscalation = problemEscalation;
	}
	public Date getTimeRaised() {
		return timeRaised;
	}
	public void setTimeRaised(Date timeRaised) {
		this.timeRaised = timeRaised;
	}
	public Date getTimechanged() {
		return timechanged;
	}
	public void setTimechanged(Date timechanged) {
		this.timechanged = timechanged;
	}
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}
	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	public Date getResolutionDate() {
		return resolutionDate;
	}
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	public String getStatusChangeReason() {
		return statusChangeReason;
	}
	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	

}
