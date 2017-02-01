package org.openoss.karaf.features.tmforum.spm.model.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangeRequest {

	private String href = null;

	private String id = null;

	private String description = null;
	private String externalId = null;
	private Date scheduledDate = null;
	private Date competionDate = null;
	private Date plannedStartTime = null;
	private Date plannedEndTime = null;
	private Date actualStartTime = null;
	private Date actualEndTime = null;
	private String status = null;
	// private RelatedParty relatedParty: relatedParty
	// impactedEntity. Entity
	// changeRequestSpecification: speciﬁcation
	// changeRequestCharacteristic: characteristic
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public Date getCompetionDate() {
		return competionDate;
	}
	public void setCompetionDate(Date competionDate) {
		this.competionDate = competionDate;
	}
	public Date getPlannedStartTime() {
		return plannedStartTime;
	}
	public void setPlannedStartTime(Date plannedStartTime) {
		this.plannedStartTime = plannedStartTime;
	}
	public Date getPlannedEndTime() {
		return plannedEndTime;
	}
	public void setPlannedEndTime(Date plannedEndTime) {
		this.plannedEndTime = plannedEndTime;
	}
	public Date getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public Date getActualEndTime() {
		return actualEndTime;
	}
	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
