package org.opennms.iotsim.model;

import java.util.Arrays;
import java.util.List;

//TODO CHANGE TO ENUM
public class IotDeviceType {

	public static final String MOBILE = "mobile";
	
	public static final String FIXED = "fixed";
	
	public static final String SIMULATION = "simulation";
	
	public static final List<String> ALLOWED_VALUES = Arrays.asList(MOBILE,FIXED,SIMULATION);
}
