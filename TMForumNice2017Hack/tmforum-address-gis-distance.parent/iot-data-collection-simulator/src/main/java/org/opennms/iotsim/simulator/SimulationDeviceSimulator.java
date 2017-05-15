package org.opennms.iotsim.simulator;

import org.opennms.iotsim.model.IotData;
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;

public class SimulationDeviceSimulator {

	IotDeviceDAO iotDeviceDao = null;

	public IotData getIotSample(IotDevice iotDevice){
		IotData iotData = new IotData();
		return iotData;
	}

	public IotDeviceDAO getIotDeviceDao() {
		return iotDeviceDao;
	}

	public void setIotDeviceDao(IotDeviceDAO iotDeviceDao) {
		this.iotDeviceDao = iotDeviceDao;
	}
	
}
