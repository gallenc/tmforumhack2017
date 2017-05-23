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

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.opennms.tmforum.address.gis.rest.model.DistanceMessage;


@ApplicationPath("iotsimulator")
public class Application extends ResourceConfig {

    public Application() {
    	register(JacksonFeature.class);
        packages (this.getClass().getPackage().getName() ,"org.codehaus.jackson.jaxrs");
    	register(org.opennms.tmforum.address.model.ObjectFactory.class);
    	register(DistanceMessage.class);
    	register(CORSFilter.class);
    }

}