package org.opennms.plugins.sniffysimulator;

import java.time.LocalDateTime;
import java.util.Date;

import org.opennms.plugins.sniffysimulator.jaxb.SniffyData;

public class SniffyDataSimulator implements SniffyDataCollector {

	@Override
	public SniffyData getMeasurement(){
		
		int hr = LocalDateTime.now().getHour(); // 0-23
		//LocalDateTime.now().getMinute(); //0-60
		
		int x = (hr - 12)*(hr - 12) / 144; // highest at mid day
		double pM1=x * 0.001;
		double pM10=x * 0.0001;
		double pM25=x * 0.00001;
		
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
