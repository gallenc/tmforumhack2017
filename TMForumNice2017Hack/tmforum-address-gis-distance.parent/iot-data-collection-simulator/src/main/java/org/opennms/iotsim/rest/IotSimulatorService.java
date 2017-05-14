
package org.opennms.iotsim.rest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.opennms.iotsim.model.IotData;
import org.opennms.iotsim.model.NameValuePair;
import org.opennms.iotsim.model.PolutionIndex;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;
import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

import com.vividsolutions.jts.geom.Coordinate;




@Path("/api/v1")
public class IotSimulatorService {





	/**
	 * http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915&maxReturnAddresses=5
	 * http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915&streetName=Itchen%20Quays
	 * http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/closestAddresses?latitude_start=50.889311&longitude_start=-1.391915&maxReturnAddresses=3
	 * @param latitude_start
	 * @param longitude_start
	 * @return
	 */
	//	@GET
	//	@Path("/closestAddresses")
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Response jsonClosestAddresses(@QueryParam("latitude_start") String latitude_start,
	//			@QueryParam("longitude_start") String longitude_start, 
	//			@QueryParam("maxReturnAddresses") String maxReturnAddressesStr, 
	//			@Context UriInfo uriInfo) {
	//
	//
	//		// get query params and remove latitude parameters
	//		MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<String, String> (uriInfo.getQueryParameters()); 
	//		queryParams.remove("latitude_start");
	//		queryParams.remove("longitude_start");
	//		queryParams.remove("maxReturnAddresses");
	//
	//		Response response = null;
	//		
	//		Properties Properties = ServiceLoader.getProperties();
	//
	//		try {
	//			if(latitude_start==null || latitude_start.isEmpty() || longitude_start==null || longitude_start.isEmpty()) 
	//				throw new IllegalArgumentException("Query parameters latitude and longitude must be set");
	//
	//			Integer maxReturnAddresses=null;
	//			if (maxReturnAddressesStr !=null){
	//				try{
	//					maxReturnAddresses=Integer.parseInt(maxReturnAddressesStr);
	//				} catch (NumberFormatException nfe){
	//					throw new IllegalArgumentException("maxReturnAddresses cannot be parsed as integer", nfe);
	//				}
	//			}
	//
	//			Properties = ServiceLoader.getProperties();
	//
	//		//	Set<DistanceMessage> foundDistances = nearestAddressFinder.findClosestAddresses(latitude_start, longitude_start, maxReturnAddresses, queryParams);
	//
	//		//	response = Response.ok(foundDistances).build();
	//		} catch (Exception exception) {
	//			Status status = Status.BAD_REQUEST;
	//			int code = 0;
	//			String message = "error in /nearestAddress";
	//			String link = null;
	//			StatusMessage statusmsg = new StatusMessage(status.getStatusCode(), code, message, link, exception);
	//			response = Response.status(status).entity(statusmsg).build();
	//		}
	//
	//		return response;
	//
	//	}


	/**
	 * Calculate the distance between 2 points a and b given lat and long gis coordinates for a and b
	 * example call http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/distance?latitude_start=50.889311&longitude_start=-1.391915&latitude_finish=50.891099&longitude_finish=-1.390925
	 * @param latitude_start
	 * @param longitude_start
	 * @param latitude_finish
	 * @param longitude_finish
	 * @return json message containing coordinates and distance
	 * {
	 *   "distance" : "227.10552897619243",
	 *   "latitude_start" : "50.889311",
	 * 	 "longitude_start" : "-1.391915",
	 *   "latitude_finish" : "50.891099",
	 *   "longitude_finish" : "-1.390925"
	 * }
	 */
	@GET
	@Path("/distance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonDistance(@QueryParam("latitude_start") String latitude_start, 
			@QueryParam("longitude_start") String longitude_start, 
			@QueryParam("latitude_finish") String latitude_finish, 
			@QueryParam("longitude_finish") String longitude_finish) {

		Response response = null;

		try {
			if(latitude_start==null || latitude_start.isEmpty() || longitude_start==null || longitude_start.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude_start and longitude_start must be set");
			if(latitude_finish==null || latitude_finish.isEmpty() || longitude_finish==null || longitude_finish.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude_finish and longitude_finish must be set");

			DistanceMessage replyDistanceMsg= new DistanceMessage();
			replyDistanceMsg.setLatitude_start(latitude_start);
			replyDistanceMsg.setLatitude_finish(latitude_finish);
			replyDistanceMsg.setLongitude_start(longitude_start);
			replyDistanceMsg.setLongitude_finish(longitude_finish);

			double distance = DistanceCalculator.distance(latitude_start, longitude_start, latitude_finish, longitude_finish);

			replyDistanceMsg.setDistance(Double.toString(distance));
			response = Response.ok(replyDistanceMsg).build();
		}

		catch (Exception exception) {
			Status status = Status.BAD_REQUEST;
			int code = 0;
			String message = "error";
			String link = null;
			StatusMessage statusmsg = new StatusMessage(status.getStatusCode(), code, message, link, exception.getMessage());
			response = Response.status(status).entity(statusmsg).build();
		}

		return response;

	}

	/**
	 * Calculate the distance between 2 points a and b given lat and long gis coordinates for a and b
	 * 
	 * @param requestDistanceMsg
	 * POST http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/distance
	 * Content-Type: application/json
	 * {
	 *   "latitude_start" : "50.889311",
	 *   "longitude_start" : "-1.391915",
	 *   "latitude_finish" : "50.891099",
	 *   "longitude_finish" : "-1.390925"
	 * }
	 *
	 * @return json message containing coordinates and distance
	 * {
	 *   "distance" : "227.10552897619243",
	 *   "latitude_start" : "50.889311",
	 *   "longitude_start" : "-1.391915",
	 *   "latitude_finish" : "50.891099",
	 *   "longitude_finish" : "-1.390925"
	 * }
	 */
	//	@POST
	//	@Path("/distance")
	//	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//	public Response jsonPostDistance(DistanceMessage requestDistanceMsg) {
	//
	//		Response response = null;
	//
	//		try {
	//			if( requestDistanceMsg==null ) 
	//				throw new IllegalArgumentException("request json DistanceMessage must not be null");
	//			response = jsonDistance(requestDistanceMsg.getLatitude_start(), 
	//					requestDistanceMsg.getLongitude_start(),requestDistanceMsg.getLatitude_finish(), requestDistanceMsg.getLongitude_finish());
	//		}
	//
	//		catch (Exception exception) {
	//			Status status = Status.BAD_REQUEST;
	//			int code = 0;
	//			String message = "error";
	//			String link = null;
	//			StatusMessage statusmsg = new StatusMessage(status.getStatusCode(), code, message, link, exception.getMessage());
	//			response = Response.status(status).entity(statusmsg).build();
	//		}
	//
	//		return response;
	//
	//	}

	@GET
	@Path("/iotsample/")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getIotSampleQuery(@QueryParam("id") String id){
		return getIotSample(id);
	}

	@GET
	@Path("/iotsample/{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getIotSample(@PathParam("id") String id){


		Response response = null;

		try {
			IotData iotData = new  IotData();
			iotData.setId(id);
			
			iotData.setLabel("label_"+id);
			
			iotData.setTimestamp(Long.toString(new Date().getTime()));
			GeoCode geocode = new GeoCode();

			// latitude_start=50.889311&longitude_start=-1.391915
			geocode.setLatitude("50.889311");

			geocode.setLongitude("-1.391915");
			iotData.setGeocode(geocode );

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

           PolutionIndex polutionIndex= new PolutionIndex();
           parameters.addAll(polutionIndex.getParameters());

			// fixed point parameters
			parameters.add(new NameValuePair("potable_Water_Litres","100.00"));
			parameters.add(new NameValuePair("waste_Water_Litres","100.00"));
			parameters.add(new NameValuePair("electricity_KwH","100.00"));
			

			iotData.setParameters(parameters );
			response = Response.ok(iotData).build();
		}

		catch (Exception exception) {
			Status status = Status.BAD_REQUEST;
			int code = 0;
			String message = "error";
			String link = null;
			StatusMessage statusmsg = new StatusMessage(status.getStatusCode(), code, message, link, exception.getMessage());
			response = Response.status(status).entity(statusmsg).build();
		}

		return response;





	}




}