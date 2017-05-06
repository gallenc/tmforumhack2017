
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
	 * http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/nearestAddress?latitude_a=50.889311&longitude_a=-1.391915&latitude_b=50.891099&longitude_b=-1.390925
	 * @param latitude_a
	 * @param longitude_a
	 * @return 
	 * {
	 *   "distance" : "198.98099446658216",
	 *   "address_b" : {
	 *      ....
	 *     "geoCode" : {
	 *       "latitude" : "50.889311",
	 *       "longitude" : "-1.391915"
	 *     }
	 *   "latitude_a" : "50.889311",
	 *   "longitude_a" : "-1.390925",
	 *   "latitude_b" : "50.891099",
	 *   "longitude_b" : "-1.390925"
	 * }
	 */
	@GET
	@Path("/nearestAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonWorld(@QueryParam("latitude_a") String latitude_a, @QueryParam("longitude_a") String longitude_a) {

		Response response = null;

		try {
			if(latitude_a==null || latitude_a.isEmpty() || longitude_a==null || longitude_a.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude and longitude must be set");

			List<Address> resultList = new ArrayList<Address>();
			Address a = new Address();
			GeoCode geocode = new GeoCode();
			geocode.setLatitude(latitude_a);
			geocode.setLongitude(longitude_a);
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
	 * example call http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/distance?latitude_a=50.889311&longitude_a=-1.391915&latitude_b=50.891099&longitude_b=-1.390925
	 * @param latitude_a
	 * @param longitude_a
	 * @param latitude_b
	 * @param longitude_b
	 * @return json message containing coordinates and distance
	 * {
	 *   "distance" : "227.10552897619243",
	 *   "latitude_a" : "50.889311",
	 * 	 "longitude_a" : "-1.391915",
	 *   "latitude_b" : "50.891099",
	 *   "longitude_b" : "-1.390925"
	 * }
	 */
	@GET
	@Path("/distance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonDistance(@QueryParam("latitude_a") String latitude_a, 
			@QueryParam("longitude_a") String longitude_a, 
			@QueryParam("latitude_b") String latitude_b, 
			@QueryParam("longitude_b") String longitude_b) {

		Response response = null;

		try {
			if(latitude_a==null || latitude_a.isEmpty() || longitude_a==null || longitude_a.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude_a and longitude_a must be set");
			if(latitude_b==null || latitude_b.isEmpty() || longitude_b==null || longitude_b.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude_b and longitude_b must be set");

			DistanceMessage replyDistanceMsg= new DistanceMessage();
			replyDistanceMsg.setLatitude_a(latitude_a);
			replyDistanceMsg.setLatitude_b(latitude_b);
			replyDistanceMsg.setLongitude_a(longitude_a);
			replyDistanceMsg.setLongitude_b(longitude_b);

			double distance = DistanceCalculator.distance(latitude_a, longitude_a, latitude_b, longitude_b);

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
	 *   "latitude_a" : "50.889311",
	 *   "longitude_a" : "-1.391915",
	 *   "latitude_b" : "50.891099",
	 *   "longitude_b" : "-1.390925"
	 * }
	 *
	 * @return json message containing coordinates and distance
	 * {
	 *   "distance" : "227.10552897619243",
	 *   "latitude_a" : "50.889311",
	 *   "longitude_a" : "-1.391915",
	 *   "latitude_b" : "50.891099",
	 *   "longitude_b" : "-1.390925"
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
			response = jsonDistance(requestDistanceMsg.getLatitude_a(), 
					requestDistanceMsg.getLongitude_a(),requestDistanceMsg.getLatitude_b(), requestDistanceMsg.getLongitude_b());
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
	 *   "address_a" : 
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
	 *   "address_b" : {
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
	 *   "address_a" : {
	 *      ....
	 *     "geoCode" : {
	 *       "latitude" : "50.889311",
	 *       "longitude" : "-1.391915"
	 *     }
	 *  },
	 *  "address_b" : {
	 *     ....
	 *    "geoCode" : {
	 *      "latitude" : "50.891099",
	 *      "longitude" : "-1.390925"
	 *    }
	 *  },
	 *   "latitude_a" : "50.889311",
	 *   "longitude_a" : "-1.390925",
	 *   "latitude_b" : "50.891099",
	 *   "longitude_b" : "-1.390925"
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
			Address address_a = requestDistanceMsg.getAddress_a();
			Address address_b = requestDistanceMsg.getAddress_b();
			if( address_a==null || address_a.getGeoCode() ==null || address_a.getGeoCode().getLatitude()==null || address_a.getGeoCode().getLongitude()==null  ) 
				throw new IllegalArgumentException("address_a gis coordinates must not be null in request message");
			if( address_b==null || address_b.getGeoCode() ==null || address_b.getGeoCode().getLatitude()==null || address_b.getGeoCode().getLongitude()==null  ) 
				throw new IllegalArgumentException("address_b gis coordinates must not be null in request message");

			replyDistanceMsg.setAddress_a(address_a);
			replyDistanceMsg.setAddress_b(address_b);

			String latitude_a = requestDistanceMsg.getAddress_a().getGeoCode().getLatitude();
			String longitude_a = requestDistanceMsg.getAddress_b().getGeoCode().getLongitude();

			String latitude_b = requestDistanceMsg.getAddress_b().getGeoCode().getLatitude();
			String longitude_b = requestDistanceMsg.getAddress_b().getGeoCode().getLongitude();

			replyDistanceMsg.setLatitude_a(latitude_a);
			replyDistanceMsg.setLatitude_b(latitude_b);
			replyDistanceMsg.setLongitude_a(longitude_a);
			replyDistanceMsg.setLongitude_b(longitude_b);

			double distance = DistanceCalculator.distance(latitude_a, longitude_a, latitude_b, longitude_b);

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