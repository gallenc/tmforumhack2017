package org.openoss.karaf.features.tmforum.spm.model.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatedServiceProblemRef {

	private String href = null;

	private String id = null;

	private String correlationId = null;

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

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
