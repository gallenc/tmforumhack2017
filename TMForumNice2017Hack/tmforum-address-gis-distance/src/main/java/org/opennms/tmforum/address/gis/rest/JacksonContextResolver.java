package org.opennms.tmforum.address.gis.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

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
        .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationConfig.Feature.INDENT_OUTPUT, true)
        .configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}