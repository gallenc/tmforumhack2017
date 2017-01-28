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

}
