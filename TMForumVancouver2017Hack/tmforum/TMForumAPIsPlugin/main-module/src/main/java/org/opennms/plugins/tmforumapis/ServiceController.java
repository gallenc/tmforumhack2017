package org.opennms.plugins.tmforumapis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceController {
	private static final Logger LOG = LoggerFactory.getLogger(ServiceController.class);

	public void init(){
		LOG.info("tmforumapis Service Controller started");
	}
	
	public void destroy(){
		LOG.info("tmforumapis Service Controller stopped");
	}
}
