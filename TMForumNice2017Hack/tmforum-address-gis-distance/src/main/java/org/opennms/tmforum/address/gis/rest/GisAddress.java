
package org.opennms.tmforum.address.gis.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

//http://localhost:8080/tmforum-address-gis-distance/gisaddress/api/v1/nearestAddress?latitude=1&longitude=5

@Path("/api/v1")
public class GisAddress {

	@GET
	@Path("/nearestAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonWorld(@QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude) {

		Response response = null;

		try {
			if(latitude==null || latitude.isEmpty() || longitude==null || longitude.isEmpty()) 
				throw new IllegalArgumentException("Query parameters latitude and longitude must be set");

			List<Address> resultList = new ArrayList<Address>();
			Address a = new Address();
			GeoCode geocode = new GeoCode();
			geocode.setLatitude(latitude);
			geocode.setLongitude(longitude);
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
}