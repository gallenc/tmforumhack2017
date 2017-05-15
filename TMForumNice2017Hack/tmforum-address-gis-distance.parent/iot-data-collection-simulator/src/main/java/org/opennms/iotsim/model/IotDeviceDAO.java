package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class IotDeviceDAO {
	
	
	// map of iot device types
    private Map<String, Map<String, IotDevice>> iotDevices = new ConcurrentHashMap<String, Map<String, IotDevice>>();

	
	public void IotService(){
		for (String deviceType :IotDeviceType.ALLOWED_VALUES){
			iotDevices.put(deviceType, new ConcurrentHashMap<String,IotDevice>());
		}
	}
    
	
	public IotDevice createDevice(IotDevice iotDevice){
		if(iotDevice.getId() !=null) throw new RuntimeException("id must be null when creating device");
		if(iotDevice.getIotDevicetype()==null 
				|| ! IotDeviceType.ALLOWED_VALUES.contains(iotDevice.getIotDevicetype()) )
			throw new RuntimeException("unknown iot device type="+iotDevice.getIotDevicetype()
					+ " for device id="+iotDevice.getId());
		iotDevice.setId(iotDevice.newId());
		
		Map<String, IotDevice> deviceIndex = iotDevices.get(iotDevice.getIotDevicetype());
		deviceIndex.put(iotDevice.id, iotDevice);
		return iotDevice;
		
	}
	
	public List<IotDevice> createDevices(List<IotDevice> iotDevices){
		List<IotDevice> created = new ArrayList<IotDevice>();
		for(IotDevice iotDevice: iotDevices){
			created.add(createDevice(iotDevice));
		}
		return created;
	}
	
	public void deleteDevice(String id){
		for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
			Map<String, IotDevice> iotDevicemap = entry.getValue();
			if (iotDevicemap.containsKey(id)){
				entry.getValue().remove(id);
				break;
			}
		}
	}
	
	public void deleteAllDevices(){
		for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
			entry.getValue().clear();
		}
		
	}
	
	public void deleteAllDevices(String deviceType){
		if(! iotDevices.containsKey(deviceType))
			throw new RuntimeException("unknown iot device type="+deviceType);
			
		iotDevices.get(deviceType).clear();
	}
	

	
	public IotDevice getDevice(String id){
		IotDevice iotDevice =null;
		for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
			Map<String, IotDevice> iotDevicemap = entry.getValue();
			if (iotDevicemap.containsKey(id)){
				iotDevice =entry.getValue().get(id);
				break;
			}
		}
		return iotDevice;
		
	}
	
	public List<IotDevice> getDevices(String deviceType){
		if(! iotDevices.containsKey(deviceType))
			throw new RuntimeException("unknown iot device type="+deviceType);
		List<IotDevice> iotDevicesList = new ArrayList<IotDevice>(iotDevices.get(deviceType).values());
        return iotDevicesList;
	}
	


}
