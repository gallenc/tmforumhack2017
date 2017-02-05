package org.openoss.karaf.features.tmforum.spm.api.service;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface Reply {

	public StatusMessage getStatusMessage();

	@XmlElement()
	public void setStatusMessage(StatusMessage statusMessage);

}