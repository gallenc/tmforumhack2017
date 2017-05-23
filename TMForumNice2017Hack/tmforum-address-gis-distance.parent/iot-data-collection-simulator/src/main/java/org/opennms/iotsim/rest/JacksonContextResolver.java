/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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