package org.openoss.karaf.features.tmforum.spm.model.event;

/** 
 * convenience methods to create notifications 
 * @author admin
 *
 */
public class ServiceProblemNotificationTypes {

	public static final String SERVICE_PROBLEM_CHANGE_NOTIFICATION ="ServiceProblemChangeNotification";
	
	public static final String SERVICE_PROBLEM_CREATION_NOTIFICATION ="ServiceProblemCreationNotification";
	
	public static final String SERVICE_PROBLEM_INFORMATION_REQUIRED_NOTIFICATION ="ServiceProblemInformationRequiredNotification";

	public static final String SERVICE_PROBLEM_STATUS_CHANGE_NOTIFICATION ="ServiceProblemStatusChangeNotification";

	public static Notification createServiceProblemChangeNotification(){
		Notification notification = new Notification();
		notification.setEventType(SERVICE_PROBLEM_CHANGE_NOTIFICATION);
		return notification;
	}
	
	public static Notification createServiceProblemCreationNotification(){
		Notification notification = new Notification();
		notification.setEventType(SERVICE_PROBLEM_CREATION_NOTIFICATION);
		return notification;
	}
	
	public static Notification createServiceProblemInformationRequiredNotification(){
		Notification notification = new Notification();
		notification.setEventType(SERVICE_PROBLEM_INFORMATION_REQUIRED_NOTIFICATION);
		return notification;
	}
	
	public static Notification createServiceProblemStatusChangeNotification(){
		Notification notification = new Notification();
		notification.setEventType(SERVICE_PROBLEM_STATUS_CHANGE_NOTIFICATION);
		return notification;
	}
}
