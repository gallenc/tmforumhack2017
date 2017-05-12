package org.opennms.iotsim.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * used to force pretty print of output
 * see http://stackoverflow.com/questions/10532217/jax-rs-json-pretty-output
 * @author cgallen
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
    private ObjectMapper objectMapper;

    public JacksonContextResolver() throws Exception {
        this.objectMapper = new ObjectMapper();
    this.objectMapper
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        .configure(SerializationFeature.INDENT_OUTPUT,true)
        .configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false)
        .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS,true);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}