package org.opennms.iotsim.simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opennms.iotsim.model.IotData;
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.model.IotDeviceType;
import org.opennms.iotsim.model.KeyValuePair;
import org.opennms.iotsim.model.PollutionIndex;
import org.opennms.iotsim.rest.DistanceCalculator;
import org.opennms.tmforum.address.model.GeoCode;

/**
 * simulator which generates absolute air pollution values values based upon index
 * @author cgallen
 *
 */
public class MobileSensorSimulator {

	IotDeviceDAO iotDeviceDao = null;

	public IotData getIotSample(IotDevice iotDevice){

		IotData iotData = new  IotData();
		iotData.setId(iotDevice.getId());
		iotData.setLabel(iotDevice.getLabel());
		GeoCode geocode = iotDevice.getGeocode();
		if(geocode !=null) iotData.setGeocode(geocode);

		long currentTimestamp = new Date().getTime();
		long startMissionTimestamp=currentTimestamp;

		// start time is iot device timestamp
		String lastTimestampStr= iotDevice.getTimestamp();
		try{
			if(lastTimestampStr!=null)  startMissionTimestamp = Long.parseLong(lastTimestampStr);
		} catch (NumberFormatException nfe){ }

		long timeSinceMissionStart = currentTimestamp - startMissionTimestamp;

		List<KeyValuePair> outparameters = new ArrayList<KeyValuePair>();

		List<GeoCode> mission = iotDevice.getMission();
		String speedStr = iotDevice.getSpeed();
		Double speed=null;
		try{
			if(speedStr!=null) speed = Double.parseDouble(speedStr);
		} catch (NumberFormatException nfe){ }
		
		double distanceTraveled = speed * timeSinceMissionStart / 1000;
		
		if (mission!=null && ! mission.isEmpty()){
			//find where we are on the mission
			double cummulativeDistance = 0;
			Iterator<GeoCode> missionItr = mission.iterator();
			GeoCode previousPoint = missionItr.next();
			while(missionItr.hasNext()){
				GeoCode waypoint = missionItr.next();
				String latitude_start=previousPoint.getLatitude();
				String longitude_start=previousPoint.getLongitude();
				String latitude_finish=waypoint.getLatitude();
				String longitude_finish=waypoint.getLongitude();
				double distance = DistanceCalculator.distance(latitude_start, longitude_start, latitude_finish, longitude_finish);
				cummulativeDistance = cummulativeDistance+distance;
				if (cummulativeDistance>=distanceTraveled) break;
				previousPoint=waypoint;
			}
			double index = calculatePolutionAtWaypoint(previousPoint);
			PollutionIndex pollutionIndex= new PollutionIndex();
			pollutionIndex.setNormalisedMeasures(index);
			outparameters.addAll(pollutionIndex.getAirPollutionParameters());
		}

		iotData.setParameters(outparameters);

		return iotData;
	}

	//https://en.wikipedia.org/wiki/Mean_inter-particle_distance
	//	https://laqm.defra.gov.uk/tools-monitoring-data/no2-falloff.html
	//	  Cz = ((Cy-Cb) / (-0.5476 x Ln(Dy) + 2.7171)) x (-0.5476*Ln(Dz)+2.7171) + Cb
	//Where:
	//Cz is the total predicted concentration (μg/m3) at distance Dz;
	//Cy is the total measured concentration (μg/m3) at distance Dy;
	//Cb is the background concentration (μg/m3);
	//Dy is the distance from the kerb at which concentrations were measured;
	//Dz is the distance from the kerb (m) at which concentrations are to be predicted;
	//
	//Ln(D) is the natural log of the number D
	//	Cz is the total predicted concentration (μg/m3) at distance Dz;
	//	double Cy; // Cz is the total predicted concentration (μg/m3) at distance Dz;
	//	double Dy; // distance from kerb at which concentrations were measured
	//	double Dz; // Dz is the distance from the kerb (m) at which concentrations are to be predicted;
	//	double Cz = ((Cy) / (-0.5476 * Math.log(Dy) + 2.7171)) * (-0.5476 * Math.log(Dz)+2.7171);

	public double calculatePolutionAtWaypoint(GeoCode waypoint){
		double totalPollution = 0;

		// additively accumulate contribution from all pollution sources
		List <IotDevice> simulationDevices = iotDeviceDao.getDevices(IotDeviceType.SIMULATION);
		for (IotDevice simulationDevice:simulationDevices){
			String latitude_finish=waypoint.getLatitude();
			String longitude_finish=waypoint.getLongitude();
			String latitude_start=simulationDevice.getGeocode().getLatitude();
			String longitude_start=simulationDevice.getGeocode().getLongitude();
			double distanceDz = DistanceCalculator.distance(latitude_start, longitude_start, latitude_finish, longitude_finish);

			List<KeyValuePair> inparameters = simulationDevice.getParameters();

			if (inparameters!=null){
				Map<String,String> inparms = new HashMap<String,String>();
				for(KeyValuePair kvp : inparameters){
					inparms.put(kvp.getKey(), kvp.getValue());
				}
				long pindex = 0;
				if (inparms.containsKey("polutionIndex")){
					pindex = Long.parseLong(inparms.get("polutionIndex"));
				}

				double measuredCy = pindex;
				// double measuredCy; // Cy is the total measured concentration (μg/m3) at distance Dy;
				// double distanceDz; // Dz is the distance from the kerb (m) at which concentrations are to be predicted;
				double predictionCz = ((measuredCy) / (2.7171)) * (-0.5476 * Math.log(distanceDz)+2.7171);
				totalPollution = totalPollution + predictionCz;
			}
		}
		return totalPollution;
	}


	public void setIotDeviceDao(IotDeviceDAO iotDeviceDao) {
		this.iotDeviceDao = iotDeviceDao;
	}

}


//		//random data
//		Double index= Math.random() * 10 ;
//		PollutionIndex pollutionIndex= new PollutionIndex();
//		pollutionIndex.setNormalisedMeasures(index);
//		outparameters.addAll(pollutionIndex.getAirPollutionParameters());

//		if (inparameters!=null){
//			Map<String,String> inparms = new HashMap<String,String>();
//			for(KeyValuePair kvp : inparameters){
//				inparms.put(kvp.getKey(), kvp.getValue());
//			}
//			if ( inparms.containsKey("polutionIndex")){
//				long pindex = Long.parseLong(inparms.get("polutionIndex"));
//				PollutionIndex polutionIndex= new PollutionIndex();
//				polutionIndex.setNormalisedMeasures(pindex);
//				outparameters.addAll(polutionIndex.getAirPollutionParameters());
//			}
//			
//		}

