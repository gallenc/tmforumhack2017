package org.opennms.plugins.sniffysimulator;

import org.opennms.plugins.sniffysimulator.jaxb.SniffyData;

public interface SniffyDataCollector {

	SniffyData getMeasurement();

}