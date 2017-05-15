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
public class IotMobileDevice extends IotDevice {
	
	List<GeoCode> mission = new ArrayList<GeoCode>();
	
	String speed = null;
}
