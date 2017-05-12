package org.opennms.tmforum.address.gis.rest;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;
import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

/**
 * Nearest address finder
 * @author cgallen
 *
 */
public class NearestAddressFinder {

	// slf4j not in glassfish?
	private static void debugLog(String msg){
		Logger.getLogger(NearestAddressFinder.class.getName()).log(Level.INFO, msg);
	}

	private static void debugLog(String msg,Exception thrown ){
		Logger.getLogger(NearestAddressFinder.class.getName()).log(Level.INFO, msg, thrown);
	}

	private Set<Address> addressCache=null;
	
	public Set<Address> getAddressCache() {
		return addressCache;
	}

	public void setAddressCache(Set<Address> addressCache) {
		this.addressCache = addressCache;
	}
	
	
	public  SortedMap<Double, DistanceMessage> getDistanceMap(String latitude_start, String longitude_start){
		SortedMap<Double, DistanceMessage> distanceMap = new TreeMap<Double, DistanceMessage>();
		
		Set<Address> addresslist = getAddressCache();
		
		for(Address address:addresslist){
			GeoCode geocode = address.getGeoCode();
			if (geocode !=null & geocode.getLatitude() !=null & geocode.getLongitude() !=null){
				try {
					String latitude_finish= geocode.getLatitude();
					String longitude_finish=geocode.getLongitude();
					Double distance = DistanceCalculator.distance(latitude_start, longitude_start, latitude_finish, longitude_finish);
					DistanceMessage distanceMessage= new DistanceMessage();
					distanceMessage.setDistance(Double.toString(distance));
					distanceMessage.setAddress_finish(address);
					distanceMessage.setLatitude_start(latitude_start);
					distanceMessage.setLatitude_finish(latitude_finish);
					distanceMessage.setLongitude_start(longitude_start);
					distanceMessage.setLongitude_finish(longitude_finish);
					
					if(distanceMap.containsKey(distance)) distance = distance+0.01; // avoid duplicate entires 1 cm offset
					distanceMap.put(distance, distanceMessage);
				} catch (Exception e){
					debugLog("problem calculating distance for address id "+ address.getId(),e);
				}
			}
			
		}
		
		return distanceMap;
	}

	/**
	 * Finds the address nearest to given point
	 * @param latitude
	 * @param longitude
	 * @return DistanceMessage containing the address and the distance from the point
	 */
	public  DistanceMessage findNearestAddress(String latitude_start, String longitude_start){
		DistanceMessage distanceMessage=null;
		
		SortedMap<Double, DistanceMessage> distanceMap = getDistanceMap( latitude_start,  longitude_start);
		
		if(distanceMap.isEmpty()) return null;
		
		distanceMessage=distanceMap.get(distanceMap.firstKey());

		return distanceMessage;
	}
	
	/**
	 * Finds the address nearest to given address
	 * @param latitude
	 * @param longitude
	 * @return DistanceMessage containing the address and the distance from the point
	 */
	public  DistanceMessage findNearestAddress(Address address){
		if(address.getGeoCode()==null) throw new IllegalArgumentException("address geocode cannot be null");
		String latitude_start = address.getGeoCode().getLatitude();
		String longitude_start = address.getGeoCode().getLongitude();
		return findNearestAddress(latitude_start, longitude_start);
	}

	/**
	 * Returns list of distance messages ordered by distance from given point. Closest first.
	 * @param latitude
	 * @param longitude
	 * @param maxReturnAddresses maximum number of addresses to return
	 * @return
	 */
	public   Set<DistanceMessage> findClosestAddresses(String latitude_start, String longitude_start, Integer maxReturnAddresses){
		Set<DistanceMessage> closestAddresses = new LinkedHashSet<DistanceMessage>();

		SortedMap<Double, DistanceMessage> distanceMap = getDistanceMap( latitude_start,  longitude_start);
		
		int i=0;
		for (Double distance : distanceMap.keySet()){
			if(maxReturnAddresses!=null && i>=maxReturnAddresses) break;
			closestAddresses.add(distanceMap.get(distance));
			i++;
		}
			
		return closestAddresses;
	}
	
	/**
	 * Returns list of distance messages ordered by distance from given address. Closest first.
	 * @param address
	 * @param maxReturnAddresses
	 * @return
	 */
	public   Set<DistanceMessage> findClosestAddresses(Address address, Integer maxReturnAddresses){
		if(address.getGeoCode()==null) throw new IllegalArgumentException("address geocode cannot be null");
		String latitude_start = address.getGeoCode().getLatitude();
		String longitude_start = address.getGeoCode().getLongitude();
		return findClosestAddresses(latitude_start, longitude_start, maxReturnAddresses);
	}


}
