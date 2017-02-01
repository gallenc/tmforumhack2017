package org.openoss.karaf.features.tmforum.spm.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification {

	private String eventType = null;
	private Date eventTime = null;
	private String eventld = null;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventld() {
		return eventld;
	}

	public void setEventld(String eventld) {
		this.eventld = eventld;
	}

}
