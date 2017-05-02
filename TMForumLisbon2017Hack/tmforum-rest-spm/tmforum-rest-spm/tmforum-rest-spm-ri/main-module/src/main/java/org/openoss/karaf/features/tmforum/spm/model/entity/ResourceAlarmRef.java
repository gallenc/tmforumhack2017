package org.openoss.karaf.features.tmforum.spm.model.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceAlarmRef {

	private String href = null;

	private String id = null;
	
	//association
	private List<ChangeRequest> changeRequest=null;

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

	/**
	 * @return the changeRequest
	 */
	public List<ChangeRequest> getChangeRequest() {
		return changeRequest;
	}

	/**
	 * @param changeRequest the changeRequest to set
	 */
	public void setChangeRequest(List<ChangeRequest> changeRequest) {
		this.changeRequest = changeRequest;
	}

}
