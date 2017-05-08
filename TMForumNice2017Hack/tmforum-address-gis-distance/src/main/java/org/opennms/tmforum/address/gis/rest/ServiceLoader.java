package org.opennms.tmforum.address.gis.rest;

import org.opennms.tmforum.address.client.TmforumAddressClient;

public class ServiceLoader {
	
	// change to match server address you are testing against
	private static final  String TMFORUM_ADDRESS_URI="http://139.162.227.142:8080/addressManagement/api/addressManagement/v1";

	private static NearestAddressFinder nearestAddressFinder=null;

	public static NearestAddressFinder getNearestAddressFinder(){
		if (nearestAddressFinder==null) {
			TmforumAddressClient addressClient = new TmforumAddressClient(TMFORUM_ADDRESS_URI);
			nearestAddressFinder = new NearestAddressFinder(addressClient);
		}
		return nearestAddressFinder;
	}
	

}
