package org.openoss.karaf.features.tmforum.spm.model.entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceProblem {
	
	// properties
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
	
	// associations
	private ImpactPattern impactPattern=null;
	
	private List<EventRef> relatedEvent =null; 
	
	private Extensionlnfo exensioninfo=null;
	
	private FirstAlert firstAlert = null;
	
	private List<TrackingRecord> trackingId=null;
	
	private List<Comment> comment=null;
	
	private List<RelatedServiceProblemRef> parentProblem=null;
	private List<RelatedServiceProblemRef> underlyingProblem=null;
	
	private OriginatorPartyRef originatorParty=null;
	
	private List<RelatedPartyRef> relatedParty=null;
	
	private ResponsiblePartyRef responsibleParty=null;
	
	private List<LocationRef> affectedLocation=null;
	
	private List<ResourceRef> affectedResource=null;
	private List<ResourceRef> rootCauseResource =null;
	
	
	private List<ServiceRef> affectedService=null;
	private List<ServiceRef> rootCauseService=null;
	
	private List<RelatedObjectRef> relatedObject=null;	
	
	private List<TroubleTicketRef> associatedTroubleTicket=null;
	
	private List<ResourceAlarmRef> underLyingAlarm=null;
	
	private List<SLAviolationRef> associatedSLAviolation=null;
	
	// getters and setters
	
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
	public ImpactPattern getImpactPattern() {
		return impactPattern;
	}
	public void setImpactPattern(ImpactPattern impactPattern) {
		this.impactPattern = impactPattern;
	}
	public List<EventRef> getRelatedEvent() {
		return relatedEvent;
	}
	public void setRelatedEvent(List<EventRef> relatedEvent) {
		this.relatedEvent = relatedEvent;
	}
	public Extensionlnfo getExensioninfo() {
		return exensioninfo;
	}
	public void setExensioninfo(Extensionlnfo exensioninfo) {
		this.exensioninfo = exensioninfo;
	}
	public FirstAlert getFirstAlert() {
		return firstAlert;
	}
	public void setFirstAlert(FirstAlert firstAlert) {
		this.firstAlert = firstAlert;
	}
	public List<TrackingRecord> getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(List<TrackingRecord> trackingId) {
		this.trackingId = trackingId;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public List<RelatedServiceProblemRef> getParentProblem() {
		return parentProblem;
	}
	public void setParentProblem(List<RelatedServiceProblemRef> parentProblem) {
		this.parentProblem = parentProblem;
	}
	public List<RelatedServiceProblemRef> getUnderlyingProblem() {
		return underlyingProblem;
	}
	public void setUnderlyingProblem(
			List<RelatedServiceProblemRef> underlyingProblem) {
		this.underlyingProblem = underlyingProblem;
	}
	public OriginatorPartyRef getOriginatorParty() {
		return originatorParty;
	}
	public void setOriginatorParty(OriginatorPartyRef originatorParty) {
		this.originatorParty = originatorParty;
	}
	public List<RelatedPartyRef> getRelatedParty() {
		return relatedParty;
	}
	public void setRelatedParty(List<RelatedPartyRef> relatedParty) {
		this.relatedParty = relatedParty;
	}
	public ResponsiblePartyRef getResponsibleParty() {
		return responsibleParty;
	}
	public void setResponsibleParty(ResponsiblePartyRef responsibleParty) {
		this.responsibleParty = responsibleParty;
	}
	public List<LocationRef> getAffectedLocation() {
		return affectedLocation;
	}
	public void setAffectedLocation(List<LocationRef> affectedLocation) {
		this.affectedLocation = affectedLocation;
	}
	public List<ResourceRef> getAffectedResource() {
		return affectedResource;
	}
	public void setAffectedResource(List<ResourceRef> affectedResource) {
		this.affectedResource = affectedResource;
	}
	public List<ResourceRef> getRootCauseResource() {
		return rootCauseResource;
	}
	public void setRootCauseResource(List<ResourceRef> rootCauseResource) {
		this.rootCauseResource = rootCauseResource;
	}
	public List<ServiceRef> getAffectedService() {
		return affectedService;
	}
	public void setAffectedService(List<ServiceRef> affectedService) {
		this.affectedService = affectedService;
	}
	public List<ServiceRef> getRootCauseService() {
		return rootCauseService;
	}
	public void setRootCauseService(List<ServiceRef> rootCauseService) {
		this.rootCauseService = rootCauseService;
	}
	public List<RelatedObjectRef> getRelatedObject() {
		return relatedObject;
	}
	public void setRelatedObject(List<RelatedObjectRef> relatedObject) {
		this.relatedObject = relatedObject;
	}
	public List<TroubleTicketRef> getAssociatedTroubleTicket() {
		return associatedTroubleTicket;
	}
	public void setAssociatedTroubleTicket(
			List<TroubleTicketRef> associatedTroubleTicket) {
		this.associatedTroubleTicket = associatedTroubleTicket;
	}
	public List<ResourceAlarmRef> getUnderLyingAlarm() {
		return underLyingAlarm;
	}
	public void setUnderLyingAlarm(List<ResourceAlarmRef> underLyingAlarm) {
		this.underLyingAlarm = underLyingAlarm;
	}
	public List<SLAviolationRef> getAssociatedSLAviolation() {
		return associatedSLAviolation;
	}
	public void setAssociatedSLAviolation(
			List<SLAviolationRef> associatedSLAviolation) {
		this.associatedSLAviolation = associatedSLAviolation;
	}
	
	
	
	
	@Override
	public String toString() {
		return "ServiceProblem [href=" + href + ", id=" + id
				+ ", correlationID=" + correlationID + ", originatingsystem="
				+ originatingsystem + ", category=" + category
				+ ", impactImportanceFactor=" + impactImportanceFactor
				+ ", priority=" + priority + ", description=" + description
				+ ", problemEscalation=" + problemEscalation + ", timeRaised="
				+ timeRaised + ", timechanged=" + timechanged
				+ ", statusChangeDate=" + statusChangeDate
				+ ", resolutionDate=" + resolutionDate
				+ ", statusChangeReason=" + statusChangeReason + ", reason="
				+ reason + ", impactPattern=" + impactPattern
				+ ", relatedEvent=" + relatedEvent + ", exensioninfo="
				+ exensioninfo + ", firstAlert=" + firstAlert + ", trackingId="
				+ trackingId + ", comment=" + comment + ", parentProblem="
				+ parentProblem + ", underlyingProblem=" + underlyingProblem
				+ ", originatorParty=" + originatorParty + ", relatedParty="
				+ relatedParty + ", responsibleParty=" + responsibleParty
				+ ", affectedLocation=" + affectedLocation
				+ ", affectedResource=" + affectedResource
				+ ", rootCauseResource=" + rootCauseResource
				+ ", affectedService=" + affectedService
				+ ", rootCauseService=" + rootCauseService + ", relatedObject="
				+ relatedObject + ", associatedTroubleTicket="
				+ associatedTroubleTicket + ", underLyingAlarm="
				+ underLyingAlarm + ", associatedSLAviolation="
				+ associatedSLAviolation + "]";
	}
	
	
	

}
