/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.tmforum.address.model.GeoCode;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class IotDevice {
	
	private static AtomicInteger idIterator = new AtomicInteger(0);
	
	String id = null; 
	
	String label=null;
	
	String iotDevicetype = null;

	GeoCode geocode = new GeoCode();
	
	String timestamp = Long.toString(new Date().getTime());
	
	List<KeyValuePair> parameters = new ArrayList<KeyValuePair>();
	
	List<GeoCode> mission = new ArrayList<GeoCode>();
	
	String speed = null;
	
	public IotDevice(String iotDeviceType){
		if(! IotDeviceType.ALLOWED_VALUES.contains(iotDeviceType))
			throw new IllegalArgumentException("unknown iotDeviceType="+ iotDeviceType);
		this.setIotDevicetype(iotDeviceType);
		this.setId(newId());
	}

	public IotDevice() {
		super();
	}

	public static String newId(){
		return Integer.toString(idIterator.addAndGet(1));
	}
	

	public String getId() {
		return id;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getIotDevicetype() {
		return iotDevicetype;
	}
	
	@XmlElement
	public void setIotDevicetype(String iotDevicetype) {
		this.iotDevicetype = iotDevicetype;
	}

	public GeoCode getGeocode() {
		return geocode;
	}

	@XmlElement
	public void setGeocode(GeoCode geocode) {
		this.geocode = geocode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<KeyValuePair> getParameters() {
		return parameters;
	}

	@XmlElement
	public void setParameters(List<KeyValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getLabel() {
		return label;
	}

	@XmlElement
	public void setLabel(String label) {
		this.label = label;
	}


	public List<GeoCode> getMission() {
		return mission;
	}

	@XmlElement
	public void setMission(List<GeoCode> mission) {
		this.mission = mission;
	}


	public String getSpeed() {
		return speed;
	}

	@XmlElement
	public void setSpeed(String speed) {
		this.speed = speed;
	}
}
