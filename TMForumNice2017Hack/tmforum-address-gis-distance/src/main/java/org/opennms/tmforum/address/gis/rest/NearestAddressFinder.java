package org.opennms.tmforum.address.gis.rest;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.opennms.tmforum.address.client.TmforumAddressClient;

public class NearestAddressFinder {
	
	TmforumAddressClient addressClient=null;
	
	public NearestAddressFinder(TmforumAddressClient addressClient){
		super();
		this.addressClient = addressClient;
	}
	
	@SuppressWarnings("unused")
	private  NearestAddressFinder(){
		super();
	};

	/**
	 * Finds the address nearest to given point
	 * @param latitude
	 * @param longitude
	 * @param queryParams query parameters to pass to address client to limit search
	 * @return DistanceMessage containing the address and the distance from the point
	 */
	public DistanceMessage findNearestAddress(String latitude, String longitude, Map<String,String> queryParams){
		DistanceMessage distanceMessage=null;
		
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
	public Set<DistanceMessage> findClosestAddresses(String latitude, String longitude, Integer maxReturnAddresses, Map<String,String> queryParams){
		Set<DistanceMessage> closestAddresses = new LinkedHashSet<DistanceMessage>();
		
		return closestAddresses;
	}
}
