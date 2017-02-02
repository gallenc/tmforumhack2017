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

package org.openoss.karaf.features.tmforum.spm.ri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * simple class to print startup and shutdown messages to karaf consol
 * @author admin
 *
 */
public class SayHello {
	private static final Logger LOG = LoggerFactory.getLogger(SayHello.class);
	
	private String helloMessage=SayHello.class.getName()+" Started (hello message not set)";
	private String goodbyeMessage=SayHello.class.getName()+" Stopped (goodbye message not set)";

	public SayHello(){
		super();
	}
	
	public void initMethod(){
		System.out.println(helloMessage);
		LOG.info(helloMessage);
	}
	
	public void destroyMethod(){
		System.out.println(goodbyeMessage);
		LOG.info(goodbyeMessage);
	}

	public String getHelloMessage() {
		return helloMessage;
	}

	public void setHelloMessage(String helloMessage) {
		this.helloMessage = helloMessage;
	}

	public String getGoodbyeMessage() {
		return goodbyeMessage;
	}

	public void setGoodbyeMessage(String goodbyeMessage) {
		this.goodbyeMessage = goodbyeMessage;
	}


}
