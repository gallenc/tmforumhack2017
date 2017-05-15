
package org.opennms.iotsim.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;


@Path("device/api/v1")
public class IotDeviceService{
	
	public void IotService() {
	}

	/**
http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/createdevice
	 * @param iotDevice
	 * @return
	 */
	@POST
	@Path("/createdevice")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response createDevice(IotDevice iotDevice) {

		Response response = null;
		try {
			if( iotDevice==null ) 
				throw new IllegalArgumentException("request json iotDevice must not be null"); 		
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			IotDevice responseDevice = iotDeviceDAO.createDevice(iotDevice);
			response = Response.ok(responseDevice).build();
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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/createdevices
	 * @param iotDevices
	 * @return
	 */
	@POST
	@Path("/createdevices")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response createDevices(List<IotDevice> iotDevices) {

		Response response = null;
		try {
			if( iotDevices==null ) 
				throw new IllegalArgumentException("request json iotDevice must not be null");
 	
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			List<IotDevice> createdIotDevices = iotDeviceDAO.createDevices(iotDevices);
			response = Response.ok(createdIotDevices).build();
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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/delete?id=1
	 * @param id
	 * @return
	 */
	@POST
	@Path("/delete")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response  deleteDevice(@QueryParam(value = "id") String id) {
		Response response = null;
		try {
			if( id==null ) 
				throw new IllegalArgumentException("request id must not be null");
			
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			iotDeviceDAO.deleteDevice(id);
			
			response = response = Response.ok().build();
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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/deletealldevices

	 * @return
	 */
	@POST
	@Path("/deletealldevices")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response  deleteAllDevices() {
		Response response = null;
		try {
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			iotDeviceDAO.deleteAllDevices();;
			
			response =Response.ok().build();
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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/deletealldeviceoftype?deviceType=simulation
    deviceType defined in IotDeviceType 
	MOBILE = "mobile";
	FIXED = "fixed";
	SIMULATION = "simulation";
	 * @param deviceType
	 * @return
	 */
	@POST
	@Path("/deletealldeviceoftype")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response  deleteAllDevicesOfType(@QueryParam(value = "deviceType") String deviceType) {
		Response response = null;
		try {
			if( deviceType==null ) 
				throw new IllegalArgumentException("request devicetype must not be null");
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			iotDeviceDAO.deleteAllDevices(deviceType);
			
			response =Response.ok().build();

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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/device?id=1
	 * @param id
	 * @return
	 */
	@GET
	@Path("/device")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response  getDevice(@QueryParam(value = "id") String id) {

		Response response = null;
		try {
			if( id==null ) 
				throw new IllegalArgumentException("request id must not be null");	
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			IotDevice responseDevice = iotDeviceDAO.getDevice(id);
			response = Response.ok(responseDevice).build();
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
	 * http://139.162.227.142:8080/iot-data-data-collection-simulator/iotsimulator/device/api/v1/devices?deviceType=simulation
   if null return all devices
    deviceType defined in IotDeviceType 
	MOBILE = "mobile";
	FIXED = "fixed";
	SIMULATION = "simulation";

	 * @param deviceType
	 * @return
	 */
	@GET
	@Path("/devices")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response  getDevices(@QueryParam(value = "deviceType") String deviceType) {

		Response response = null;
		try {
			IotDeviceDAO iotDeviceDAO= ServiceLoader.getIotDeviceDao();
			List<IotDevice> retreivedIotDevices = iotDeviceDAO.getDevices(deviceType);
			response = Response.ok(retreivedIotDevices).build();
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