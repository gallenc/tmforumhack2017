package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.tmforum.address.model.GeoCode;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IotData {
	
	String id = null;
	
	String deviceLabel = null;

	GeoCode geocode = new GeoCode();
	
	String timestamp = Long.toString(new Date().getTime());
	
	List<NameValuePair> parameters = new ArrayList<NameValuePair>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GeoCode getGeocode() {
		return geocode;
	}

	public void setGeocode(GeoCode geocode) {
		this.geocode = geocode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<NameValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<NameValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getDeviceLabel() {
		return deviceLabel;
	}

	public void setLabel(String deviceLabel) {
		this.deviceLabel = deviceLabel;
	}
}
