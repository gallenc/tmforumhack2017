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

package org.opennms.tmforum.address.gis.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.opennms.tmforum.address.model.Address;


/**
 * used to generate error response messages
 * error handling suggestion taken from http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/
 * @author cgallen@opennms.org
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType (propOrder={"distance", "latitude_start", "longitude_start", "latitude_finish", "longitude_finish", "address_start", "address_finish",})
public class DistanceMessage {

	String distance=null;

	//@XmlElement(nillable = true)
	Address address_start=null;
	
	//@XmlElement(nillable = true)
	Address address_finish=null;

	String latitude_start;
	String longitude_start; 

	String latitude_finish;
	String longitude_finish;

	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Address getAddress_start() {
		return address_start;
	}
	public void setAddress_start(Address address_start) {
		this.address_start = address_start;
	}
	public Address getAddress_finish() {
		return address_finish;
	}
	public void setAddress_finish(Address address_finish) {
		this.address_finish = address_finish;
	}
	public String getLatitude_start() {
		return latitude_start;
	}
	public void setLatitude_start(String latitude_start) {
		this.latitude_start = latitude_start;
	}
	public String getLongitude_start() {
		return longitude_start;
	}
	public void setLongitude_start(String longitude_start) {
		this.longitude_start = longitude_start;
	}
	public String getLatitude_finish() {
		return latitude_finish;
	}
	public void setLatitude_finish(String latitude_finish) {
		this.latitude_finish = latitude_finish;
	}
	public String getLongitude_finish() {
		return longitude_finish;
	}
	public void setLongitude_finish(String longitude_finish) {
		this.longitude_finish = longitude_finish;
	}
	
	@Override
	public String toString() {
		return "DistanceMessage [distance=" + distance + ", address_start=" + address_start + ", address_finish="
				+ address_finish + ", latitude_start=" + latitude_start + ", longitude_start=" + longitude_start
				+ ", latitude_finish=" + latitude_finish + ", longitude_finish=" + longitude_finish + "]";
	}



}