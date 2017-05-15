package org.opennms.iotsim.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class IotDeviceDAO   {
	
	
	// map of iot device types
    private Map<String, Map<String, IotDevice>> iotDevices = new ConcurrentHashMap<String, Map<String, IotDevice>>();

	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#IotService()
	 */
	public void IotService(){
		for (String deviceType :IotDeviceType.ALLOWED_VALUES){
			iotDevices.put(deviceType, new ConcurrentHashMap<String,IotDevice>());
		}
	}
    
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#createDevice(org.opennms.iotsim.model.IotDevice)
	 */
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
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#createDevices(java.util.List)
	 */
	public List<IotDevice> createDevices(List<IotDevice> iotDevices){
		List<IotDevice> created = new ArrayList<IotDevice>();
		for(IotDevice iotDevice: iotDevices){
			created.add(createDevice(iotDevice));
		}
		return created;
	}
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#deleteDevice(java.lang.String)
	 */
	public void deleteDevice(String id){
		for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
			Map<String, IotDevice> iotDevicemap = entry.getValue();
			if (iotDevicemap.containsKey(id)){
				entry.getValue().remove(id);
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#deleteAllDevices()
	 */
	public void deleteAllDevices(){
		for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
			entry.getValue().clear();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#deleteAllDevices(java.lang.String)
	 */
	public void deleteAllDevices(String deviceType){
		if(! iotDevices.containsKey(deviceType))
			throw new RuntimeException("unknown iot device type="+deviceType);
			
		iotDevices.get(deviceType).clear();
	}
	

	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#getDevice(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see org.opennms.iotsim.model.IotDeviceApi#getDevices(java.lang.String)
	 */
	public List<IotDevice> getDevices(String deviceType){
		if(deviceType==null){
			List<IotDevice> iotDevicesList = new ArrayList<IotDevice>();
			for(Entry<String, Map<String, IotDevice>> entry:iotDevices.entrySet()){
				Map<String, IotDevice> iotDevicemap = entry.getValue();
				iotDevicesList.addAll(iotDevicemap.values());
			}
			return iotDevicesList;
		} else {
		if(! iotDevices.containsKey(deviceType))
			throw new RuntimeException("unknown iot device type="+deviceType);
		List<IotDevice> iotDevicesList = new ArrayList<IotDevice>(iotDevices.get(deviceType).values());
        return iotDevicesList;
		}
	}
	


}
