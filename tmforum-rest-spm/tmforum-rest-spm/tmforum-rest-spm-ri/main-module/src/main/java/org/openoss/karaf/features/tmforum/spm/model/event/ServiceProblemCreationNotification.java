package org.openoss.karaf.features.tmforum.spm.model.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceProblemCreationNotification extends Notification {
	
	private String eventType = "ServiceProblemCreationNotification";

}
