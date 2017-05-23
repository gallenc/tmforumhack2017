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
 * simulator which generates absolute air pollution values values based upon index
 * @author cgallen
 *
 */
public class EmissionSimulator {

	IotDeviceDAO iotDeviceDao = null;

	public IotData getIotSample(IotDevice iotDevice){

		IotData iotData = new  IotData();
		iotData.setId(iotDevice.getId());
		iotData.setLabel(iotDevice.getLabel());
		GeoCode geocode = iotDevice.getGeocode();
		if(geocode !=null) iotData.setGeocode(geocode);

		long timestamp = new Date().getTime();
		long lasttimestamp=timestamp;

		String lastTimestampStr= iotDevice.getTimestamp();
		try{
			if(lastTimestampStr!=null)  lasttimestamp = Long.parseLong(lastTimestampStr);
		} catch (NumberFormatException nfe){ }
		iotData.setTimestamp(Long.toString(timestamp));

		long msSinceLastSample = timestamp - lasttimestamp;

		List<KeyValuePair> outparameters = new ArrayList<KeyValuePair>();

		List<KeyValuePair> inparameters = iotDevice.getParameters();
		if (inparameters!=null){
			Map<String,String> inparms = new HashMap<String,String>();
			for(KeyValuePair kvp : inparameters){
				inparms.put(kvp.getKey(), kvp.getValue());
			}
			if ( inparms.containsKey("polutionIndex")){
				Double pindex = Double.parseDouble(inparms.get("polutionIndex"));
				PollutionIndex polutionIndex= new PollutionIndex();
				polutionIndex.setNormalisedMeasures(pindex);
				outparameters.addAll(polutionIndex.getAirPollutionParameters());
			}
			
		}
		iotData.setParameters(outparameters );

		return iotData;
	}


	public void setIotDeviceDao(IotDeviceDAO iotDeviceDao) {
		this.iotDeviceDao = iotDeviceDao;
	}

}
