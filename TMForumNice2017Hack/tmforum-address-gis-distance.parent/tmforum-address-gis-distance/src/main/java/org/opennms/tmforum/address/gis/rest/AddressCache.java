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

package org.opennms.tmforum.address.gis.rest;

import java.util.Date;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;

import org.opennms.tmforum.address.client.TmforumAddressClient;
import org.opennms.tmforum.address.model.Address;

/**
 * AddressCache caches address list for 5 minutes unless the query params change
 * @author cgallen
 *
 */
public class AddressCache {

	// slf4j not in glassfish?
	private static void debugLog(String msg){
		Logger.getLogger(AddressCache.class.getName()).log(Level.INFO, msg);
	}

	private static void debugLog(String msg,Exception thrown ){
		Logger.getLogger(AddressCache.class.getName()).log(Level.INFO, msg, thrown);
	}


	public static final int MAX_ADDRESS_AGE = 1000*60*5; // 5 minutes
	public static final int MAX_CACHED_QUERIES=10; // maximum number of cached queries before overwrite

	private Map<String,CachedAddressSet> addressCache = new ConcurrentHashMap<String,CachedAddressSet>();

	private TmforumAddressClient addressClient=null;

	public AddressCache(TmforumAddressClient addressClient){
		super();
		this.addressClient = addressClient;
	}

	@SuppressWarnings("unused")
	private  AddressCache(){
		super();
	}

	public Set<Address> getCachedAddresses(MultivaluedMap<String,String> queryParams) {

		String queryParamsStr = (queryParams==null || queryParams.isEmpty()) ? "NULL" : queryParams.toString();

		CachedAddressSet cachedAddressSet = addressCache.get(queryParamsStr);

		debugLog("getCachedAddresses.queryParamsStr: "+queryParamsStr+ 
				" addressCache.size() "+addressCache.size()
				+ " cachedAddressSet: " +( (cachedAddressSet==null) ? "null" : cachedAddressSet.getAddressSet().size()) );

		if(cachedAddressSet==null
				|| (cachedAddressSet.getAddressSet().isEmpty()) 
				|| (new Date().getTime() - cachedAddressSet.getLastRefreshTime() ) > MAX_ADDRESS_AGE) {
			return updateAddressCache(queryParamsStr, queryParams);
		} else {
			return cachedAddressSet.getAddressSet();
		}

	}

	private Set<Address> updateAddressCache( String queryParamsStr, MultivaluedMap<String,String> queryParams) {
		try{
			Set<Address> addressSetNew = addressClient.getAddresses(queryParams);
			debugLog("addressSetNew.size: "+addressSetNew.size());
			CachedAddressSet cachedAddressSet = new CachedAddressSet(addressSetNew);
			debugLog("cachedAddressSet.size: "+cachedAddressSet.getAddressSet().size());
			addressCache.put(queryParamsStr, cachedAddressSet);
			if(addressCache.size() > MAX_CACHED_QUERIES ) {
				debugLog("clearing out old ceche entries addressCache.size() "+ addressCache.size()
				+ " > MAX_CACHED_QUERIES: "+MAX_CACHED_QUERIES); 
				// remove oldest value and values with age  > MAX_ADDRESS_AGE in synchronised block
				synchronized (addressCache) {
					Iterator<Map.Entry<String, CachedAddressSet>> cacheIterator = addressCache.entrySet().iterator();
					long currentTime = new Date().getTime();
					Map.Entry<String, CachedAddressSet> oldestEntry=null;
					while (cacheIterator.hasNext()) {
						Map.Entry<String, CachedAddressSet> entry = cacheIterator.next();
						if(currentTime - entry.getValue().getLastRefreshTime() > MAX_ADDRESS_AGE) {
							cacheIterator.remove();
						} else {
							if(oldestEntry==null) {
								oldestEntry = entry;
							} else if (entry.getValue().getLastRefreshTime() < oldestEntry.getValue().getLastRefreshTime()){
								oldestEntry = entry;
							}
						}
					}
					if(addressCache.size() > MAX_CACHED_QUERIES ){
						if (oldestEntry !=null ) addressCache.remove(oldestEntry);
					}
				}
			}
			debugLog("cachedAddressSet.size: "+addressCache.get(queryParamsStr).getAddressSet().size());
			return addressSetNew;
		} catch (Exception e){
			debugLog("problem updating address cache with queryParams "+queryParamsStr ,e);
			throw new RuntimeException("problem updating address cache with queryParams "+queryParamsStr ,e);
		}
	}

	private class CachedAddressSet {

		private final Set<Address> addressSet= new LinkedHashSet<Address>();
		private final long lastRefreshTime = new Date().getTime();

		@SuppressWarnings("unused")
		private CachedAddressSet() {};

		public CachedAddressSet(Set<Address> addressSet){
			this.addressSet.addAll(addressSet);
		}

		public Set<Address> getAddressSet() {
			return addressSet;
		}

		public long getLastRefreshTime() {
			return lastRefreshTime;
		}


	}



















}