package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.List;

/**
 * see https://en.wikipedia.org/wiki/Air_quality_index#United_Kingdom
 * @author cgallen
 *
 */
public class PolutionIndex {

	
	public List<NameValuePair> getParameters(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		  // mobile parameters
		parameters.add(new NameValuePair("ozone_μg_m3","100.00"));
		parameters.add(new NameValuePair("nitrogenDioxide_μg_m3","100.00"));
		parameters.add(new NameValuePair("SulphurDioxide_μg_m3","100.00"));
		parameters.add(new NameValuePair("pM2.5Particles_μg_m3","100.00"));
		parameters.add(new NameValuePair("pM10Particles_μg_m3","100.00"));
		
		// 1-10
		parameters.add(new NameValuePair("estimated_UK_Polution_index","10"));
		
		parameters.add(new NameValuePair("estimated_UK_Air_pollution_banding","Severe"));
		
		return parameters;
	}
	
}
