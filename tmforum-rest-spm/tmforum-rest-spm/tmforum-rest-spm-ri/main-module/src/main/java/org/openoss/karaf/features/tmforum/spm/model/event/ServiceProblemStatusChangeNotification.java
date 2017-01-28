package org.openoss.karaf.features.tmforum.spm.model.event;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceProblemStatusChangeNotification extends Notification {
	
	private String eventType = "ServiceProblemStatusChangeNotification";

}
