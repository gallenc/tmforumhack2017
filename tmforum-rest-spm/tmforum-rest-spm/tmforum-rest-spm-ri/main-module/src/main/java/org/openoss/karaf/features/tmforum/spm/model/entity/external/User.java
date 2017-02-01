package org.openoss.karaf.features.tmforum.spm.model.entity.external;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private String href;

	private String id;

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
}
