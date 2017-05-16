
package org.opennms.iotsim.rest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
import org.opennms.iotsim.model.IotDevice;
import org.opennms.iotsim.model.IotDeviceDAO;
import org.opennms.iotsim.model.IotDeviceType;
import org.opennms.iotsim.model.KeyValuePair;
import org.opennms.iotsim.model.PollutionIndex;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;
import org.opennms.tmforum.address.model.Address;
import org.opennms.tmforum.address.model.GeoCode;

import com.vividsolutions.jts.geom.Coordinate;




@Path("/api/v1")
public class IotSimulatorService {

	@GET
	@Path("/iotsamplelabel")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getIotSampleDeviceLabelQuery(@QueryParam("label") String label){
		return getIotDeviceLabelQuery(label);
	}

	@GET
	@Path("/iotsamplelabel/{label}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getIotDeviceLabelQuery(@PathParam("label") String label){
		Response response = null;

		try {
			if (label==null) throw new IllegalArgumentException("cannot use null device label="+label);

			IotData iotData=null;

			IotDeviceDAO iotDeviceDAO = ServiceLoader.getIotDeviceDao();

			List<IotDevice> iotDevices = iotDeviceDAO.getDevices(null); // all devices

			IotDevice iotDevice=null;
			for (IotDevice device:iotDevices){
				if(label.equals(device.getLabel())) {
					iotDevice=device;
					break;
				}
			}
			if (iotDevice==null) throw new IllegalArgumentException("cannot get sample for unknown device label="+label);

			iotData= getSample(iotDevice);

			response = Response.ok(iotData).build();
		} catch (Exception exception) {
			Status status = Status.BAD_REQUEST;
			int code = 0;
			String message = "error";
			String link = null;
			StatusMessage statusmsg = new StatusMessage(status.getStatusCode(), code, message, link, exception.getMessage());
			response = Response.status(status).entity(statusmsg).build();
		}

		return response;

	}


	IotData getSample(IotDevice iotDevice){

		IotData	iotData=null;
		String deviceType = iotDevice.getIotDevicetype();

		if(IotDeviceType.FIXED.equals(deviceType)) {

			iotData = ServiceLoader.getFixedDeviceSimulator().getIotSample(iotDevice);

		} else if(IotDeviceType.MOBILE.equals(deviceType)) {

			iotData = ServiceLoader.getMobiledevicesimulator().getIotSample(iotDevice);

		} else if(IotDeviceType.SIMULATION.equals(deviceType)) {

			iotData = ServiceLoader.getSimulationDeviceSimulator().getIotSample(iotDevice);

		} else  throw new IllegalStateException("unknow device type "+deviceType+ " for device label="+iotDevice.getLabel());

		return iotData;
	}

	@GET
	@Path("/iotsample")
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
			if (id==null) throw new IllegalArgumentException("cannot use null device id="+id);

			IotData iotData=null;

			IotDeviceDAO iotDeviceDAO = ServiceLoader.getIotDeviceDao();

			IotDevice iotDevice = iotDeviceDAO.getDevice(id);
			if (iotDevice==null) throw new IllegalArgumentException("cannot get sample for unknown device id="+id);

			String deviceType = iotDevice.getIotDevicetype();

			if(IotDeviceType.FIXED.equals(deviceType)) {

				iotData = ServiceLoader.getFixedDeviceSimulator().getIotSample(iotDevice);

			} else if(IotDeviceType.MOBILE.equals(deviceType)) {

				iotData = ServiceLoader.getMobiledevicesimulator().getIotSample(iotDevice);

			} else if(IotDeviceType.SIMULATION.equals(deviceType)) {

				iotData = ServiceLoader.getSimulationDeviceSimulator().getIotSample(iotDevice);

			} else  throw new IllegalStateException("unknow device type "+deviceType+ " for device id="+id);

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