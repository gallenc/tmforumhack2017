package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.List;

/**
 * see https://en.wikipedia.org/wiki/Air_quality_index#United_Kingdom
 * @author cgallen
 *
 */
public class PolutionIndex {

	//public static final String Low 	

	String getPolutionBanding(int index){
		if ( index <1 || index > 10) throw new IllegalArgumentException("polution banding must be in range 1-10 not "+index);
		String polutionBanding=null;
		switch (index) {
		case 1: case 2: case 3:  polutionBanding = "Low"; // low 1-3
		break;
		case 4: case 5:case 6:  polutionBanding = "Moderate"; // Moderate 	4–6
		break;
		case 7: case 8: case 9:  polutionBanding = "Moderate"; // High 	7–9
		break;
		case 10:  polutionBanding = "VeryHigh"; // Very High 10
		break;
		}
		return polutionBanding;
	}


	public List<NameValuePair> getParameters(){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		// mobile parameters
		parameters.add(new NameValuePair("ozone_ug_m3","241")); // 8 hr mean
		parameters.add(new NameValuePair("nitrogenDioxide_ug_m3","601")); //1 hr mean
		parameters.add(new NameValuePair("sulphurDioxide_ug_m3","1065")); // 15 min mean
		parameters.add(new NameValuePair("pM2.5Particles_ug_m3","71")); // 24 hr mean
		parameters.add(new NameValuePair("pM10Particles_ug_m3","101")); // 24 hr mean

		// 1-10
		parameters.add(new NameValuePair("estimated_UK_Polution_index","10")); //0-10

		parameters.add(new NameValuePair("estimated_UK_Air_pollution_banding","Very_High"));

		return parameters;
	}

}
