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

}
