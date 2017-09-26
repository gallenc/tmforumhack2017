package org.opennms.plugins.tmforumapis;

import org.opennms.plugins.tmforumapis.jaxb.SniffyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SniffyServiceImpl implements SniffyService {
	private static final Logger LOG = LoggerFactory.getLogger(SniffyServiceImpl.class);

	@Override
	public SniffyData getSniffyData() {
		// TODO Auto-generated method stub
		return new SniffyData();
	}
	

	public void init(){
		LOG.info("tmforumapis SniffyServiceImpl started");
	}
	
	public void destroy(){
		LOG.info("tmforumapis SniffyServiceImpl stopped");
	}

}
