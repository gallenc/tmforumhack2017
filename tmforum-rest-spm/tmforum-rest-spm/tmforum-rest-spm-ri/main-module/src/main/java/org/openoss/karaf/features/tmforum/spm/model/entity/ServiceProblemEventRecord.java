package org.openoss.karaf.features.tmforum.spm.model.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class ServiceProblemEventRecord {
	private String href = null;

	private String id = null;

	private Date recordTime = null;
	private String eventType = null;

}
