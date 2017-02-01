package org.openoss.karaf.features.tmforum.spm.model.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelatedPartyRef {

	private String href = null;

	private String id = null;

	private String role = null;
	
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



}
