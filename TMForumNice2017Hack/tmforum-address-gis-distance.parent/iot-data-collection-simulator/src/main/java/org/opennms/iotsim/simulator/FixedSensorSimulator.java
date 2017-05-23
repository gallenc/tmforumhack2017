/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opennms.iotsim.simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opennms.iotsim.model.IotData;
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.model.KeyValuePair;
import org.opennms.iotsim.model.PollutionIndex;
import org.opennms.tmforum.address.model.GeoCode;

/**
 * simulator which generates cumulative values based upon consumption rates
 * @author cgallen
 *
 */
public class FixedSensorSimulator {
	
	private static long starttime = new Date().getTime();

	IotDeviceDAO iotDeviceDao = null;

	public IotData getIotSample(IotDevice iotDevice){

		IotData iotData = new  IotData();
		iotData.setId(iotDevice.getId());
		iotData.setLabel(iotDevice.getLabel());
		GeoCode geocode = iotDevice.getGeocode();
		if(geocode !=null) iotData.setGeocode(geocode);

		long timestamp = new Date().getTime();
		
		long lasttimestamp=starttime;

//		String lastTimestampStr= iotDevice.getTimestamp();
//		try{
//			if(lastTimestampStr!=null)  lasttimestamp = Long.parseLong(lastTimestampStr);
//		} catch (NumberFormatException nfe){
//			throw new IllegalStateException("cannot parse last timestamp "+lastTimestampStr);
//		}
		
		iotData.setTimestamp(Long.toString(timestamp));

		long msSinceLastSample = timestamp - lasttimestamp;

		List<KeyValuePair> outparameters = new ArrayList<KeyValuePair>();

		List<KeyValuePair> inparameters = iotDevice.getParameters();
		if (inparameters!=null){
			Map<String,String> inparms = new HashMap<String,String>();
			for(KeyValuePair kvp : inparameters){
				inparms.put(kvp.getKey(), kvp.getValue());
			}
			if ( inparms.containsKey("potable_Water_LitresPerSec")){
				double waterrate = Double.parseDouble(inparms.get("potable_Water_LitresPerSec"));
				String waterstr = Double.toString(waterrate * msSinceLastSample /1000);
				outparameters.add(new KeyValuePair("potable_Water_Litres",waterstr));
			}
			if ( inparms.containsKey("waste_Water_LitresPerSec")){
				double wasterate = Double.parseDouble(inparms.get("waste_Water_LitresPerSec"));
				String wastestr = Double.toString(wasterate * msSinceLastSample /1000);
				outparameters.add(new KeyValuePair("waste_Water_Litres",wastestr ));
			}
			if ( inparms.containsKey("electricity_Kw")){
				double electricityrate = Double.parseDouble(inparms.get("electricity_Kw"));
				String electricitystr = Double.toString(electricityrate * msSinceLastSample /1000);
				outparameters.add(new KeyValuePair("electricity_KwH",electricitystr));
			}
			if ( inparms.containsKey("dataConsumption_GbPerSec")){
				double datarate = Double.parseDouble(inparms.get("dataConsumption_GbPerSec"));
				String datastr = Double.toString(datarate * msSinceLastSample /1000);
				outparameters.add(new KeyValuePair("dataConsumption_Gb",datastr));
			}

		}
		iotData.setParameters(outparameters );
		
		return iotData;
	}


	public void setIotDeviceDao(IotDeviceDAO iotDeviceDao) {
		this.iotDeviceDao = iotDeviceDao;
	}

}
