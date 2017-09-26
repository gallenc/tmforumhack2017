/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2002-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.plugins.messagenotifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class simply prints out the received notification. Used primarily for testing.
 * @author admin
 *
 */
public class VerySimpleMessageNotificationClient implements NotificationClient {
	private static 	final Logger LOG = LoggerFactory.getLogger(MessageNotificationClientQueueImpl.class);

	public VerySimpleMessageNotificationClient(){
		LOG.debug("VerySimpleMessageNotificationClient initialised");
	}
	
	
	@Override
	public void sendMessageNotification(MessageNotification messageNotification) {
		if(LOG.isDebugEnabled()) LOG.debug("Notification received by VerySimpleMessageNotificationClient :\n topic:"+messageNotification.getTopic()
				+ "\n qos:"+messageNotification.getQos()
				+ "\n payload:"+new String(messageNotification.getPayload()));

	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
