package org.opennms.iotsim.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyValuePair {
	
	String key = null;
	String value = null;
 
	public KeyValuePair(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public KeyValuePair() {
	}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
