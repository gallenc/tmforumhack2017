package org.opennms.iotsim.simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.opennms.iotsim.model.IotData;
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.model.KeyValuePair;
import org.opennms.iotsim.model.PollutionIndex;
import org.opennms.tmforum.address.model.GeoCode;

public class FixedDeviceSimulator {

	private static AtomicInteger water = new AtomicInteger(0);
	private static AtomicInteger waste = new AtomicInteger(0);
	private static AtomicInteger electricity = new AtomicInteger(0);
	private static AtomicInteger data = new AtomicInteger(0);
	
	IotDeviceDAO iotDeviceDao = null;

	public IotData getIotSample(IotDevice iotDevice){
		
		IotData iotData = new  IotData();
		iotData.setId(iotDevice.getId());

		iotData.setLabel(iotDevice.getId());

		long timestamp = new Date().getTime();
		iotData.setTimestamp(Long.toString(timestamp));
		GeoCode geocode = new GeoCode();
		
		//double offset = Math.floorMod(timestamp, 1000) / 10000 ;
	
		// latitude_start=50.889311&longitude_start=-1.391915
		geocode.setLatitude("50.889311");
		geocode.setLongitude("-1.391915");
		iotData.setGeocode(geocode );

		List<KeyValuePair> parameters = new ArrayList<KeyValuePair>();

		PollutionIndex polutionIndex= new PollutionIndex();
		polutionIndex.setNormalisedMeasures(10);

		parameters.addAll(polutionIndex.getAirPollutionParameters());

		// fixed point parameters

		String waterstr = Integer.toString(water.getAndAdd(100));
		String wastestr = Integer.toString(waste.getAndAdd(100));
		String electricitystr = Integer.toString(electricity.getAndAdd(100)); 
		String datastr = Integer.toString(data.getAndAdd(100)); 


		parameters.add(new KeyValuePair("potable_Water_Litres",waterstr));
		parameters.add(new KeyValuePair("waste_Water_Litres",wastestr ));
		parameters.add(new KeyValuePair("electricity_KwH",electricitystr));
		parameters.add(new KeyValuePair("dataConsumption_Gb",datastr));


		iotData.setParameters(parameters );
		
		
		
		
		
		
		return iotData;
	}

	public IotDeviceDAO getIotDeviceDao() {
		return iotDeviceDao;
	}

	public void setIotDeviceDao(IotDeviceDAO iotDeviceDao) {
		this.iotDeviceDao = iotDeviceDao;
	}
	
}
