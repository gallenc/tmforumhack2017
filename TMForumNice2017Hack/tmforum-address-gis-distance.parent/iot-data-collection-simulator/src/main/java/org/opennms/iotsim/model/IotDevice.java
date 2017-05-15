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
	
	Date timestamp = new Date();
	
	List<NameValuePair> parameters = new ArrayList<NameValuePair>();

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

	public Date getTimestamp() {
		return timestamp;
	}

	@XmlElement
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	@XmlElement
	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getLabel() {
		return label;
	}

	@XmlElement
	public void setLabel(String label) {
		this.label = label;
	}
}
