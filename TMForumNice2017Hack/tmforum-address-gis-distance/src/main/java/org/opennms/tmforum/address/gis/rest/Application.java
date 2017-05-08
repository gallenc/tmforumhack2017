package org.opennms.tmforum.address.gis.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@ApplicationPath("gisaddress")
public class Application extends ResourceConfig {

    public Application() {
    	register(JacksonFeature.class);
        packages (this.getClass().getPackage().getName() ,"org.codehaus.jackson.jaxrs");
    	register(org.opennms.tmforum.address.model.ObjectFactory.class);
    }

}