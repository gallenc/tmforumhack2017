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

package org.openoss.karaf.features.tmforum.spm.ri;

/** used to statically pass service references to Jersey ReST classes
 * 
 * @author cgallen
 *
 */
public class ServiceLoader {
	
	private static ServiceProblemService serviceProblemService=null;

	public static synchronized ServiceProblemService getServiceProblemService() {
		return serviceProblemService;
	}

	public static synchronized void setServiceProblemService(ServiceProblemService serviceProblemService) {
		ServiceLoader.serviceProblemService = serviceProblemService;
	}

	public ServiceLoader(){
		super();
	}

	public ServiceLoader(ServiceProblemService serviceProblemService){
		super();
		setServiceProblemService(serviceProblemService);

	}


}
