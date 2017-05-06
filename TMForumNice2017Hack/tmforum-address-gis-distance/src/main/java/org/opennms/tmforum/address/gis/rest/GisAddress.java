
package org.opennms.tmforum.address.gis.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;



@Path("/api/v1")
public class GisAddress {

	/**
	 * http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/nearestAddress?latitude_start=50.889311&longitude_start=-1.391915&latitude_finish=50.891099&longitude_finish=-1.390925
	 * @param latitude_start
	 * @param longitude_start
	 * @return 
	 * {
	 *   "distance" : "198.98099446658216",
	 *   "address_finish" : {
	 *      ....
	 *     "geoCode" : {
	 *       "latitude" : "50.889311",
	 *       "longitude" : "-1.391915"
	 *     }
	 *   "latitude_start" : "50.889311",
	 *   "longitude_start" : "-1.390925",
	 *   "latitude_finish" : "50.891099",
	 *   "longitude_finish" : "-1.390925"
	 * }
	 */
	@GET
	@Path("/nearestAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonWorld(@QueryParam("latitude_start") String latitude_start, @QueryParam("longitude_start") String longitude_start) {

		Response response = null;

		try {
			if(latitude_start==null || latitude_start.isEmpty() || longitude_start==null || longitude_start.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude and longitude must be set");

			List<Address> resultList = new ArrayList<Address>();
			Address a = new Address();
			GeoCode geocode = new GeoCode();
			geocode.setLatitude(latitude_start);
			geocode.setLongitude(longitude_start);
			a.setGeoCode(geocode);
			resultList.add(a);

			response = Response.ok(resultList).build();
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
	@POST
	@Path("/distance")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response jsonPostDistance(DistanceMessage requestDistanceMsg) {

		Response response = null;

		try {
			if( requestDistanceMsg==null ) 
				throw new IllegalArgumentException("request json DistanceMessage must not be null");
			response = jsonDistance(requestDistanceMsg.getLatitude_start(), 
					requestDistanceMsg.getLongitude_start(),requestDistanceMsg.getLatitude_finish(), requestDistanceMsg.getLongitude_finish());
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
	 * Calculate the distance between 2 addresses with gis coordinates  a and b given DistanceMessage request
	 * POST http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/distance-address
	 * Content-Type: application/json
	 * {
	 *   "address_start" : 
	 * 	{
	 * 		"streetNr": "31",
	 * 		"streetNrSuffix": "Birth",
	 * 		"streetName": "Itchen Quays",
	 * 		"streetType": "Warf",
	 * 		"locality": "Multi Deck RoRo Terminal",
	 * 		"city": "Southampton",
	 * 		"stateOrProvince": "Hampshire",
	 * 		"country": "UK",
	 * 		"geoCode": {
	 * 			"latitude": "50.889311",
	 * 			"longitude": "-1.391915"
	 * 		}
	 * 	},
	 *   "address_finish" : {
	 * 		"streetNr": "32",
	 * 		"streetNrSuffix": "Birth",
	 * 		"streetName": "Itchen Quays",
	 * 		"streetType": "Warf",
	 * 		"locality": "Multi Deck RoRo Terminal",
	 * 		"city": "Southampton",
	 * 		"stateOrProvince": "Hampshire",
	 * 		"country": "UK",
	 * 		"geoCode": {
	 * 			"latitude": "50.891099",
	 * 			"longitude": "-1.390925"
	 * 		}
	 * 	}
	 * 
	 * @return json message containing addresses and distance
	 * 
	 * {
	 *   "distance" : "198.98099446658216",
	 *   "address_start" : {
	 *      ....
	 *     "geoCode" : {
	 *       "latitude" : "50.889311",
	 *       "longitude" : "-1.391915"
	 *     }
	 *  },
	 *  "address_finish" : {
	 *     ....
	 *    "geoCode" : {
	 *      "latitude" : "50.891099",
	 *      "longitude" : "-1.390925"
	 *    }
	 *  },
	 *   "latitude_start" : "50.889311",
	 *   "longitude_start" : "-1.390925",
	 *   "latitude_finish" : "50.891099",
	 *   "longitude_finish" : "-1.390925"
	 * }
	 * @param requestDistanceMsg
	 * @return
	 */
	@POST
	@Path("/distance-address")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response jsonAddressDistance(DistanceMessage requestDistanceMsg) {

		Response response = null;

		try {
			if( requestDistanceMsg==null ) 
				throw new IllegalArgumentException("request json must not be null");

			DistanceMessage replyDistanceMsg= new DistanceMessage();
			Address address_start = requestDistanceMsg.getAddress_start();
			Address address_finish = requestDistanceMsg.getAddress_finish();
			if( address_start==null || address_start.getGeoCode() ==null || address_start.getGeoCode().getLatitude()==null || address_start.getGeoCode().getLongitude()==null  ) 
				throw new IllegalArgumentException("address_start gis coordinates must not be null in request message");
			if( address_finish==null || address_finish.getGeoCode() ==null || address_finish.getGeoCode().getLatitude()==null || address_finish.getGeoCode().getLongitude()==null  ) 
				throw new IllegalArgumentException("address_finish gis coordinates must not be null in request message");

			replyDistanceMsg.setAddress_start(address_start);
			replyDistanceMsg.setAddress_finish(address_finish);

			String latitude_start = requestDistanceMsg.getAddress_start().getGeoCode().getLatitude();
			String longitude_start = requestDistanceMsg.getAddress_finish().getGeoCode().getLongitude();

			String latitude_finish = requestDistanceMsg.getAddress_finish().getGeoCode().getLatitude();
			String longitude_finish = requestDistanceMsg.getAddress_finish().getGeoCode().getLongitude();

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

}