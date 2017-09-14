package org.opennms.plugins.messagenotifier;

public interface MessageNotifier {

	void addMessageNotificationClient(MessageNotificationClient messageNotificationClient);

	void removeMessageNotificationClient(MessageNotificationClient messageNotificationClient);

}
