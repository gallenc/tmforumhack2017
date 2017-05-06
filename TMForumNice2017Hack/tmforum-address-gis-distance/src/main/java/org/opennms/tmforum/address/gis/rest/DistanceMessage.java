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

package org.opennms.tmforum.address.gis.rest;

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
//@XmlType (propOrder={"distance", "latitude_a", "longitude_a", "latitude_b", "longitude_b", "address_a", "address_b",})
public class DistanceMessage {

	String distance=null;

	//@XmlElement(nillable = true)
	Address address_a=null;
	
	//@XmlElement(nillable = true)
	Address address_b=null;

	String latitude_a;
	String longitude_a; 

	String latitude_b;
	String longitude_b;

	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Address getAddress_a() {
		return address_a;
	}
	public void setAddress_a(Address address_a) {
		this.address_a = address_a;
	}
	public Address getAddress_b() {
		return address_b;
	}
	public void setAddress_b(Address address_b) {
		this.address_b = address_b;
	}
	public String getLatitude_a() {
		return latitude_a;
	}
	public void setLatitude_a(String latitude_a) {
		this.latitude_a = latitude_a;
	}
	public String getLongitude_a() {
		return longitude_a;
	}
	public void setLongitude_a(String longitude_a) {
		this.longitude_a = longitude_a;
	}
	public String getLatitude_b() {
		return latitude_b;
	}
	public void setLatitude_b(String latitude_b) {
		this.latitude_b = latitude_b;
	}
	public String getLongitude_b() {
		return longitude_b;
	}
	public void setLongitude_b(String longitude_b) {
		this.longitude_b = longitude_b;
	}



}