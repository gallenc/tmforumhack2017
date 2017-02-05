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

package org.openoss.karaf.features.tmforum.spm.impl.service.rest;

import java.util.concurrent.atomic.AtomicBoolean;

import org.openoss.karaf.features.tmforum.spm.api.service.ServiceProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** used to statically pass service references to Jersey ReST classes
 * 
 * @author cgallen
 *
 */
public class ServiceLoader {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceLoader.class);

	private static ServiceProblemService serviceProblemService=null;

	private static AtomicBoolean errorReply = new AtomicBoolean(true);

	public static synchronized ServiceProblemService getServiceProblemService() {
		return serviceProblemService;
	}

	public static synchronized void setServiceProblemService(ServiceProblemService serviceProblemService) {
		ServiceLoader.serviceProblemService = serviceProblemService;
	}

	public ServiceLoader(){
		super();
	}

	public ServiceLoader(ServiceProblemService serviceProblemService, boolean errorReply){
		super();
		setServiceProblemService(serviceProblemService);
		setErrorReply(errorReply);

	}

	/**
	 * used to provide logging in jersey restlet - avoiding problems with class loading of logger
	 * @return
	 */
	public static Logger getLog() {
		return LOG;
	}

	public static boolean getErrorReply() {
		return errorReply.get();
	}

	public static void setErrorReply(boolean errorReply) {
		ServiceLoader.errorReply.set(errorReply);;
	}


}
