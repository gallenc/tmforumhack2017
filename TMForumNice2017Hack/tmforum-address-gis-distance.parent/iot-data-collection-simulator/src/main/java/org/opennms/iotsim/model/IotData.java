package org.opennms.iotsim.model;

import java.text.DecimalFormat;
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

	String latitudeIntegerDigits=null;

	String latitudeFractionalDigits=null;

	String longitudeIntegerDigits=null;

	String longitudeFractionalDigits=null;

	public String getLatitudeIntegerDigits() {
		return latitudeIntegerDigits;
	}

	public void setLatitudeIntegerDigits(String latitudeIntegerDigits) {
		this.latitudeIntegerDigits = latitudeIntegerDigits;
	}

	public String getLatitudeFractionalDigits() {
		return latitudeFractionalDigits;
	}

	public void setLatitudeFractionalDigits(String latitudeFractionalDigits) {
		this.latitudeFractionalDigits = latitudeFractionalDigits;
	}

	public String getLongitudeIntegerDigits() {
		return longitudeIntegerDigits;
	}

	public void setLongitudeIntegerDigits(String longitudeIntegerDigits) {
		this.longitudeIntegerDigits = longitudeIntegerDigits;
	}

	public String getLongitudeFractionalDigits() {
		return longitudeFractionalDigits;
	}

	public void setLongitudeFractionalDigits(String longitudeFractionalDigits) {
		this.longitudeFractionalDigits = longitudeFractionalDigits;
	}

	public void setDeviceLabel(String deviceLabel) {
		this.deviceLabel = deviceLabel;
	}

	String timestamp = Long.toString(new Date().getTime());

	List<KeyValuePair> parameters = new ArrayList<KeyValuePair>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GeoCode getGeocode() {
		return geocode;
	}

	// https://gis.stackexchange.com/questions/8650/measuring-accuracy-of-latitude-and-longitude  fifth decimal place is worth up to 1.1 m
	public void setGeocode(GeoCode geocode) {
		this.geocode = geocode;
		if(geocode!=null){
			try {
				DecimalFormat df = new DecimalFormat("#");
				df.setMaximumFractionDigits(6);
				df.setMinimumFractionDigits(1);
				df.setMinimumIntegerDigits(1);

				Double lat = Double.parseDouble(geocode.getLatitude());
				String latStr = df.format(lat);
				String[] latSplit = latStr.split("\\.");
				this.setLatitudeFractionalDigits(latSplit[1]);
				this.setLatitudeIntegerDigits(latSplit[0]);

				Double lon = Double.parseDouble(geocode.getLongitude());
				String lonStr = df.format(lon);
				String[] lonSplit = latStr.split("\\.");
				this.setLongitudeFractionalDigits(lonSplit[1]);
				this.setLongitudeIntegerDigits(lonSplit[0]);
			} catch (Exception e){
				throw new IllegalArgumentException("cannot parse geocode lat and long correctly ",e);
			}
		}
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<KeyValuePair> getParameters() {
		return parameters;
	}

	public void setParameters(List<KeyValuePair> parameters) {
		this.parameters = parameters;
	}

	public String getDeviceLabel() {
		return deviceLabel;
	}

	public void setLabel(String deviceLabel) {
		this.deviceLabel = deviceLabel;
	}
}
