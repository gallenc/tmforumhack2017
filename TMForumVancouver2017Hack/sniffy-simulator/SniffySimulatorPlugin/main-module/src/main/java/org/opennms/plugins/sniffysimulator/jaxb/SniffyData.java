package org.opennms.plugins.sniffysimulator.jaxb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.NONE)
public class SniffyData {
	public static final String JSON_DATE_FORMAT="yyyy-MM-dd'T'HH:mm:ssz";
	
	private String time = jsonTime(new Date());
	private String id=null;
	private String cityName=null;
	private String stationName=null;
	private String latitude=null;
	private String longitude=null;
	private String averaging=null;
	private String PM1=null;
	private String PM25=null;
	private String PM10=null;

	public String getTime() {
		return time;
	}

	@XmlElement()
	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	@XmlElement()
	public void setId(String id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	@XmlElement()
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStationName() {
		return stationName;
	}

	@XmlElement()
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLatitude() {
		return latitude;
	}

	@XmlElement()
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	@XmlElement()
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAveraging() {
		return averaging;
	}

	@XmlElement()
	public void setAveraging(String averaging) {
		this.averaging = averaging;
	}

	public String getPM1() {
		return PM1;
	}

	@XmlElement()
	public void setPM1(String pM1) {
		PM1 = pM1;
	}

	public String getPM25() {
		return PM25;
	}

	@XmlElement()
	public void setPM25(String pM25) {
		PM25 = pM25;
	}

	public String getPM10() {
		return PM10;
	}

	@XmlElement()
	public void setPM10(String pM10) {
		PM10 = pM10;
	}

	public static Date jsonToDate(String jsonDate){
		SimpleDateFormat df = new SimpleDateFormat(JSON_DATE_FORMAT);
		Date date =null;
		try {
			date = df.parse(jsonDate);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException("cannot parse supplied json date="+jsonDate,e);
		}
	}

	public static String jsonTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat( JSON_DATE_FORMAT );

		TimeZone tz = TimeZone.getTimeZone( "UTC" );

		df.setTimeZone( tz );

		String output = df.format( date );
		return output;
	}

	@Override
	public String toString() {
		return "SniffyData [time=" + time + ", id=" + id + ", cityName="
				+ cityName + ", stationName=" + stationName + ", latitude="
				+ latitude + ", longitude=" + longitude + ", averaging="
				+ averaging + ", PM1=" + PM1 + ", PM25=" + PM25 + ", PM10="
				+ PM10 + "]";
	}
	
	
}
