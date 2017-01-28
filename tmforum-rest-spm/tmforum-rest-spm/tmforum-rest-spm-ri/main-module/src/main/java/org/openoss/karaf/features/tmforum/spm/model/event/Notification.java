package org.openoss.karaf.features.tmforum.spm.model.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification {
	
	private String eventType = null;
	private Date eventTime = null;
	private String eventld  = null;

}
