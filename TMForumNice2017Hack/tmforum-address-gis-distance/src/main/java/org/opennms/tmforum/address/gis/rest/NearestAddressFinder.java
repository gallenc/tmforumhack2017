package org.opennms.tmforum.address.gis.rest;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;

import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

/**
 * Nearest address finder caches address list for 5 minutes unless the query params change
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

	public static final int MAX_ADDRESS_AGE = 1000*60*5; // 5 minutes

	private TmforumAddressClient addressClient=null;

	private Set<Address> addressCache= new LinkedHashSet<Address>();
	private long addressCacheLastRefresh = new Date().getTime();
	private String cachedQueryParamsStr="";
	
	public NearestAddressFinder(TmforumAddressClient addressClient){
		super();
		this.addressClient = addressClient;
	}

	@SuppressWarnings("unused")
	private  NearestAddressFinder(){
		super();

	}

	private Set<Address> getAddressCache(MultivaluedMap<String,String> queryParams) {
		
		String queryParamsStr = (queryParams==null) ? "" : queryParams.toString();
		if (!queryParamsStr.equals(cachedQueryParamsStr)
				||	addressCache.isEmpty() 
				|| (new Date().getTime() - addressCacheLastRefresh > MAX_ADDRESS_AGE) ){
			try{
				debugLog("trying to load new address cache");
				Set<Address> addressCacheNew = addressClient.getAddresses(queryParams);
				addressCache=addressCacheNew;
			} catch (Exception e){
				debugLog("problem loading new address cache",e);
			}
		}
		return addressCache;
	}
	
	public synchronized SortedMap<Double, DistanceMessage> getDistanceMap(String latitude_start, String longitude_start, MultivaluedMap<String,String> queryParams){
		SortedMap<Double, DistanceMessage> distanceMap = new TreeMap<Double, DistanceMessage>();
		
		Set<Address> addresslist = getAddressCache(queryParams);
		
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
	 * @param queryParams query parameters to pass to address client to limit search
	 * @return DistanceMessage containing the address and the distance from the point
	 */
	public synchronized DistanceMessage findNearestAddress(String latitude_start, String longitude_start, MultivaluedMap<String,String> queryParams){
		DistanceMessage distanceMessage=null;
		
		SortedMap<Double, DistanceMessage> distanceMap = getDistanceMap( latitude_start,  longitude_start, queryParams);
		
		if(distanceMap.isEmpty()) return null;
		
		distanceMessage=distanceMap.get(distanceMap.firstKey());

		return distanceMessage;
	}

	/**
	 * Returns list of distance messages ordered by distance from given point. Closest first.
	 * @param latitude
	 * @param longitude
	 * @param queryParams query parameters to pass to address client to limit search
	 * @param maxReturnAddresses maximum number of addresses to return
	 * @return
	 */
	public  synchronized Set<DistanceMessage> findClosestAddresses(String latitude_start, String longitude_start, Integer maxReturnAddresses, MultivaluedMap<String,String> queryParams){
		Set<DistanceMessage> closestAddresses = new LinkedHashSet<DistanceMessage>();

		SortedMap<Double, DistanceMessage> distanceMap = getDistanceMap( latitude_start,  longitude_start, queryParams);
		
		int i=0;
		for (Double distance : distanceMap.keySet()){
			if(maxReturnAddresses!=null && i>=maxReturnAddresses) break;
			closestAddresses.add(distanceMap.get(distance));
			i++;
		}
			
		return closestAddresses;
	}
}
