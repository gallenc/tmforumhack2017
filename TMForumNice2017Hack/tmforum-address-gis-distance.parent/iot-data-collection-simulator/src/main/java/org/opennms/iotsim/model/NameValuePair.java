package org.opennms.iotsim.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NameValuePair {
 
	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public NameValuePair() {
	}

	String name = null;
	String value = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
