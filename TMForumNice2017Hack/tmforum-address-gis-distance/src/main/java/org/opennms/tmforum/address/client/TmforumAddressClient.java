package org.opennms.tmforum.address.client;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.opennms.tmforum.address.model.Address;

public class TmforumAddressClient {

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

	private TmforumAddressClient(){
		super();
	}

	private Client getClient(){
		ClientConfig clientConfig=new  ClientConfig().register(JacksonJsonProvider.class);
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
	 * @param queryMap query parameters or null
	 * @return set of addresses
	 */
	public Set<Address> getAddresses(Map<String, String> queryMap){

		Client client = getClient();

		WebTarget webTarget = (client.target(tmforumAddressUriStr).path("address"));

		if (queryMap != null)
			for (Map.Entry<String, String> entry: queryMap.entrySet())
				webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());

		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if ( response.getStatus() != Response.Status.OK.getStatusCode() ) {
			throw new RuntimeException("remote responded with status: "+response.getStatusInfo());
		}

		Set<Address> addressList = response.readEntity(new GenericType<Set<Address>>() {});
		return addressList;
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

		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if ( response.getStatus() != Response.Status.OK.getStatusCode() ) {
			return null;
		}

		Address address = response.readEntity(Address.class);
		return address;
	}


}
