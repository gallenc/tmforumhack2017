package org.opennms.plugins.sniffysimulator;

import java.time.LocalDateTime;
import java.util.Date;

import org.opennms.plugins.sniffysimulator.jaxb.SniffyData;

public class SniffyDataSimulator implements SniffyDataCollector {

	@Override
	public SniffyData getMeasurement(){
		
		int hr = LocalDateTime.now().getHour(); // 0-23
		int min = LocalDateTime.now().getMinute(); //0-60
		
		double x =  Math.random() * 10 /(1+ (min-30)) + (1  + ((hr - 12)*(hr - 12)) )/ 144; // highest at mid day random hourly variations
		double pM1=x * 0.01;
		double pM10=x * 0.001;
		double pM25=x * 0.0001;
		
		SniffyData sdata =new SniffyData();
		sdata.setTime(SniffyData.jsonTime(new Date()));

		String pM1Str = Double.toString(pM1);
		sdata.setPM1(pM1Str);
		String pM10Str= Double.toString(pM10);
		sdata.setPM10(pM10Str);
		String pM25Str= Double.toString(pM25);
		sdata.setPM25(pM25Str);
		return sdata;
	}
}
