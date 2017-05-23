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

package org.opennms.tmforum.address.client;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.opennms.tmforum.address.model.Address;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TmforumAddressClient {

	// slf4j not in glassfish?
	private static void debugLog(String msg){
		Logger.getLogger(TmforumAddressClient.class.getName()).log(Level.INFO, msg);
	}

	private static void debugLog(String msg,Exception thrown ){
		Logger.getLogger(TmforumAddressClient.class.getName()).log(Level.INFO, msg, thrown);
	}

	private String tmforumAddressUriStr;
	private String username=null;
	private String password=null;

	/**
	 * constructor for TmforumAddressClient must provide uri of the tmforum address server
	 * e.g. http://139.162.227.142:8080/addressManagement/api/addressManagement/v1
	 * @param tmforumAddressUriStr
	 */
	public TmforumAddressClient(String tmforumAddressUriStr){
		super();
		this.tmforumAddressUriStr = tmforumAddressUriStr;
	}

	/**
	 * constructor for TmforumAddressClient must provide uri of the tmforum address server
	 * e.g. http://139.162.227.142:8080/addressManagement/api/addressManagement/v1
	 * @param tmforumAddressUriStr
	 * @param username
	 * @param password
	 */
	public TmforumAddressClient(String tmforumAddressUriStr, String username, String password){
		super();
		this.tmforumAddressUriStr = tmforumAddressUriStr;
		this.username=username;
		this.password=password;
	}

	@SuppressWarnings("unused")
	private TmforumAddressClient(){
		super();
	}

	private Client getClient(){
		//ClientConfig clientConfig=new  ClientConfig().register(JacksonFeature.class);
		// .register( LoggingFilter.class ) if you want to log responses

		ClientConfig clientConfig=new  ClientConfig();
		// .register( LoggingFilter.class ) if you want to log responses

		if(username!=null){
			HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(username, password);
			clientConfig.register(authFeature);
		}

		Client client = ClientBuilder.newClient(clientConfig);
		return client;
	}

	/**
	 * get list of tmforum addresses using query parameters in supplied map<name value> ?name=value
	 * e.g. http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address?streetNr=30
	 * @param queryParams query parameters or null
	 * @return set of addresses
	 */
	public Set<Address> getAddresses(MultivaluedMap<String, String> queryParams){

		Client client = getClient();

		WebTarget webTarget = (client.target(tmforumAddressUriStr).path("address"));

		if (queryParams != null) 
			for (Entry<String, List<String>> entry: queryParams.entrySet()){
				for(String value : entry.getValue())
					webTarget = webTarget.queryParam(entry.getKey(),value );
			}

		debugLog("client getAddressesURI="+webTarget.getUri());

		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if ( response.getStatus() != Response.Status.OK.getStatusCode() ) {
			throw new RuntimeException("remote responded with status: "+response.getStatusInfo());
		}

		String responseString = response.readEntity(String.class);

		//using objectmapper outside of jersey because of classpath problems in glassfish
		ObjectMapper objectMapper = new ObjectMapper();

		Set<Address> addressSet;
		try {
			// place values in received order in set
			List<Address> addressList = objectMapper.readValue(responseString, new TypeReference<List<Address>>(){});
			addressSet= new LinkedHashSet<Address>(addressList);
		} catch (IOException e) {
			debugLog("error parsing getAddresses reponse: ",e);
			throw new RuntimeException("error parsing getAddresses reponse: ",e);
		}

		//Set<Address> addressList = response.readEntity(new GenericType<Set<Address>>() {});
		return addressSet;
	}

	/**
	 * Returns address for given id or null if doesn't exist
	 * e.g. GET http://139.162.227.142:8080/addressManagement/api/addressManagement/v1/address/214
	 * @param id
	 * @return
	 */
	public Address getAddress(String id){

		Client client = getClient();

		WebTarget webTarget = (client.target(tmforumAddressUriStr).path("address").path(id));

		debugLog("client getAddressesURI="+webTarget.getUri());

		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if ( response.getStatus() != Response.Status.OK.getStatusCode() ) {
			return null;
		}

		String responseString = response.readEntity(String.class);

		ObjectMapper objectMapper = new ObjectMapper();

		Address address;
		try {
			address = objectMapper.readValue(responseString, Address.class);
		} catch (IOException e) {
			debugLog("error parsing get getAddress reponse: ",e);
			throw new RuntimeException("error parsing get addresses reponse: ",e);
		}

		//Address address = response.readEntity(Address.class);
		return address;
	}


}
