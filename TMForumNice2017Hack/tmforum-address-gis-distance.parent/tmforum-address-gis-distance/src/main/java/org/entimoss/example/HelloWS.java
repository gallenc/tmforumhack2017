
package org.entimoss.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//http://localhost:8080/tmforum-address-gis-distance/example/hello/json/ricky
//http://localhost:8080/tmforum-address-gis-distance/example/hello/text/ricky
//http://localhost:8080/tmforum-address-gis-distance/example/hello/xml/ricky

@Path("/hello")
public class HelloWS {
    
    private HelloResponse sayHello(String name) {
        return new HelloResponse(name);
    }
    
    @GET
    @Path("/any/{name}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public HelloResponse  worldany(@PathParam("name") String name) {
        return sayHello(name);
    }
    
    @GET
    @Path("/text/{name}")
    @Produces({MediaType.TEXT_PLAIN})
    public String world(@PathParam("name") String name) {
        return sayHello(name).toString();
    }

    @GET
    @Path("/xml/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public HelloResponse xmlWorld(@PathParam("name") String name) {
        return sayHello(name);
    }

    @GET
    @Path("/json/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public HelloResponse jsonWorld(@PathParam("name") String name) {
        return sayHello(name);
    }
}