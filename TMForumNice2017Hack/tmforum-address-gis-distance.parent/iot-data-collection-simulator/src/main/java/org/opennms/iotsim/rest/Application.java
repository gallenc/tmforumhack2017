package org.opennms.iotsim.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;


@ApplicationPath("gisaddress")
public class Application extends ResourceConfig {

    public Application() {
    	register(JacksonFeature.class);
        packages (this.getClass().getPackage().getName() ,"org.codehaus.jackson.jaxrs");
    	register(org.opennms.tmforum.address.model.ObjectFactory.class);
    	register(DistanceMessage.class);
    	register(CORSFilter.class);
    }

}