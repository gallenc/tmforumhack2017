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
 
 package org.opennms.plugins.mqtt.config;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mqtt-receiver")
@XmlAccessorType(XmlAccessType.NONE)
public class MQTTReceiverConfig {

	private Set<MQTTClientConfig> mqttClients=null;
	
	private Set<JsonDataParserConfig> jsonDataParsers=null;
	
	private Set<JsonEventParserConfig> jsonEventParsers=null;
	
	private Integer maxQueueLength = 1000; // default message queue length
	
	public Set<MQTTClientConfig> getMqttClients() {
		return mqttClients;
	}

	@XmlElementWrapper
	@XmlElement(name="client")
	public void setMqttClients(Set<MQTTClientConfig> mqttClients) {
		this.mqttClients = mqttClients;
	}


	public Set<JsonDataParserConfig> getJsonDataParsers() {
		return jsonDataParsers;
	}

	@XmlElementWrapper
	@XmlElement(name="jsonDataParser")
	public void setJsonDataParsers(Set<JsonDataParserConfig> jsonDataParsers) {
		this.jsonDataParsers = jsonDataParsers;
	}

	public Set<JsonEventParserConfig> getJsonEventParsers() {
		return jsonEventParsers;
	}

	@XmlElementWrapper
	@XmlElement(name="jsonEventParser")
	public void setJsonEventParsers(Set<JsonEventParserConfig> jsonEventParsers) {
		this.jsonEventParsers = jsonEventParsers;
	}

	public Integer getMaxQueueLength() {
		return maxQueueLength;
	}

	@XmlElement
	public void setMaxQueueLength(Integer macQueueLength) {
		this.maxQueueLength = macQueueLength;
	}

	

}
