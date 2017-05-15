package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.List;

/**
 * see https://en.wikipedia.org/wiki/Air_quality_index#United_Kingdom
 * @author cgallen
 *
 */
public class PollutionIndex {


	private Double ozone=null;
	private Double nitrogenDioxide=null;
	private Double sulphurDioxide=null;
	private Double pM2_5Particles=null;
	private Double pM10Particles=null;

	public Double getOzone() {
		return ozone;
	}

	public void setOzone(Double ozone) {
		this.ozone = ozone;
	}

	public Double getNitrogenDioxide() {
		return nitrogenDioxide;
	}

	public void setNitrogenDioxide(Double nitrogenDioxide) {
		this.nitrogenDioxide = nitrogenDioxide;
	}

	public Double getSulphurDioxide() {
		return sulphurDioxide;
	}

	public void setSulphurDioxide(Double sulphurDioxide) {
		this.sulphurDioxide = sulphurDioxide;
	}

	public Double getpM2_5Particles() {
		return pM2_5Particles;
	}

	public void setpM2_5Particles(Double pM2_5Particles) {
		this.pM2_5Particles = pM2_5Particles;
	}

	public Double getpM10Particles() {
		return pM10Particles;
	}

	public void setpM10Particles(Double pM10Particles) {
		this.pM10Particles = pM10Particles;
	}

	public int getOzoneIndex() {
		if (ozone==null) return 0;
		if(ozone<33) return 1;
		if(ozone<66) return 2;
		if(ozone<100) return 3;
		if(ozone<120) return 4;
		if(ozone<140) return 5;
		if(ozone<160) return 6;
		if(ozone<187) return 7;
		if(ozone<213) return 8;
		if(ozone<240) return 9;
		return 10;
	}


	public int getNitrogenDioxideIndex() {
		if (nitrogenDioxide==null) return 0;
		if(nitrogenDioxide<67) return 1;
		if(nitrogenDioxide<134) return 2;
		if(nitrogenDioxide<200) return 3;
		if(nitrogenDioxide<267) return 4;
		if(nitrogenDioxide<334) return 5;
		if(nitrogenDioxide<400) return 6;
		if(nitrogenDioxide<467) return 7;
		if(nitrogenDioxide<534) return 8;
		if(nitrogenDioxide<600) return 9;
		return 10;
	}


	public int getSulphurDioxideIndex() {
		if (sulphurDioxide==null) return 0;
		if(sulphurDioxide<88) return 1;
		if(sulphurDioxide<177) return 2;
		if(sulphurDioxide<266) return 3;
		if(sulphurDioxide<354) return 4;
		if(sulphurDioxide<443) return 5;
		if(sulphurDioxide<532) return 6;
		if(sulphurDioxide<710) return 7;
		if(sulphurDioxide<887) return 8;
		if(sulphurDioxide<1064) return 9;
		return 10;
	}



	public int getpM2_5ParticlesIndex() {
		if (pM2_5Particles==null) return 0;
		if(pM2_5Particles<11) return 1;
		if(pM2_5Particles<23) return 2;
		if(pM2_5Particles<35) return 3;
		if(pM2_5Particles<41) return 4;
		if(pM2_5Particles<47) return 5;
		if(pM2_5Particles<53) return 6;
		if(pM2_5Particles<58) return 7;
		if(pM2_5Particles<64) return 8;
		if(pM2_5Particles<70) return 9;
		return 10;
	}

	public int getpM10ParticlesIndex() {
		if (pM10Particles==null) return 0;
		if(pM10Particles<16) return 1;
		if(pM10Particles<33) return 2;
		if(pM10Particles<50) return 3;
		if(pM10Particles<58) return 4;
		if(pM10Particles<66) return 5;
		if(pM10Particles<75) return 6;
		if(pM10Particles<83) return 7;
		if(pM10Particles<91) return 8;
		if(pM10Particles<100) return 9;
		return 10;
	}


	public int getEstimated_UK_Polution_index() {
		int index = getOzoneIndex();
		if (getNitrogenDioxideIndex()>index) index =  getNitrogenDioxideIndex();
		if (getSulphurDioxideIndex()>index) index =  getSulphurDioxideIndex();
		if (getpM2_5ParticlesIndex() >index) index =  getpM2_5ParticlesIndex();
		if (getpM10ParticlesIndex() >index) index =  getpM10ParticlesIndex();
		return index;
	}


	/**
	 * returns banding as a string
	 * low 1-3
	 * Moderate 	4–6
	 * "High"; // High 	7–9
	 * "VeryHigh"; // Very High 10
	 * @param index
	 * @return
	 */
	String getPolutionBandingString(){
		int index = getEstimated_UK_Polution_index();

		String polutionBanding=null;
		switch (index) {
		case 1: case 2: case 3:  polutionBanding = "Low"; // low 1-3
		break;
		case 4: case 5:case 6:  polutionBanding = "Moderate"; // Moderate 	4–6
		break;
		case 7: case 8: case 9:  polutionBanding = "High"; // High 	7–9
		break;
		case 10:  polutionBanding = "VeryHigh"; // Very High 10
		break;
		}
		return polutionBanding;
	}

	/**
	 * returns banding as an integer
	 * Note integers range 3 to 6 to match opennms severity mapping to pollution banding
	 * https://wiki.opennms.org/wiki/Severity
	 * Critical (7)  (VeryHigh over sustained period) Purple : #C00
	 * Major (6) =   VeryHigh   Red : #F30
	 * Minor (5)  =  High       Orange : #F90
	 * Warning (4) = Moderate   Yellow : #FC0
	 * Normal (3) =  Low        Green
	 */
	int getPolutionBandingValue(){
		int index = getEstimated_UK_Polution_index();
		if ( index <1 || index > 10) throw new IllegalArgumentException("polution banding must be in range 1-10 not "+index);
		int polutionBanding=0;
		switch (index) {
		case 1: case 2: case 3:  polutionBanding = 3; // low 1-3
		break;
		case 4: case 5:case 6:  polutionBanding = 4; // Moderate 	4–6
		break;
		case 7: case 8: case 9:  polutionBanding = 5; // High 	7–9
		break;
		case 10:  polutionBanding = 6; // Very High 10
		break;
		}
		return polutionBanding;
	}

	public List<KeyValuePair> getAirPollutionParameters(){
		List<KeyValuePair> parameters = new ArrayList<KeyValuePair>();

		if (ozone!=null) parameters.add(new KeyValuePair("ozone_ug_m3",Double.toString(ozone))); // 8 hr mean
		if (nitrogenDioxide!=null) parameters.add(new KeyValuePair("nitrogenDioxide_ug_m3",Double.toString(nitrogenDioxide))); //1 hr mean
		if (sulphurDioxide!=null) parameters.add(new KeyValuePair("sulphurDioxide_ug_m3",Double.toString(sulphurDioxide))); // 15 min mean
		if (pM2_5Particles!=null) parameters.add(new KeyValuePair("pM2_5Particles_ug_m3",Double.toString(pM2_5Particles))); // 24 hr mean
		if (pM10Particles!=null) parameters.add(new KeyValuePair("pM10Particles_ug_m3", Double.toString(pM10Particles))); // 24 hr mean

		parameters.add(new KeyValuePair("estimated_UK_Polution_index",Integer.toString(getEstimated_UK_Polution_index()))); //1-10
		parameters.add(new KeyValuePair("estimated_UK_Air_pollution_banding",getPolutionBandingString()));
		parameters.add(new KeyValuePair("estimated_UK_Air_pollution_banding_Int",Integer.toString(getPolutionBandingValue())));

		return parameters;
	}

	public static final double  ozoneMax= 241;
	public static final double  nitrogenDioxideMax=601;
	public static final double  sulphurDioxideMax=1065;
	public static final double  pM2_5ParticlesMax= 71;
	public static final double  pM10ParticlesMax=101;

	/**
	 * used to set normalised measures of pollutants
	 * index range 0 to 10
	 * @param index
	 */
	public void setNormalisedMeasures(double index ){

		this.setNitrogenDioxide(index * ozoneMax / 10);
		this.setOzone(index *ozoneMax / 10);
		this.setpM10Particles(index * pM10ParticlesMax / 10);
		this.setpM2_5Particles(index * pM2_5ParticlesMax / 10);
		this.setSulphurDioxide(index * sulphurDioxideMax / 10);

	}

}
