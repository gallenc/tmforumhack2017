package org.entimoss.example;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;


//see http://blogs.nologin.es/rickyepoderi/index.php?/archives/114-Implementing-a-Jersey-2-Application-with-Maven.html

@ApplicationPath("/example")
public class Application extends ResourceConfig {

    public Application() {
    	register(JacksonFeature.class);
        packages(this.getClass().getPackage().getName());
    }

}