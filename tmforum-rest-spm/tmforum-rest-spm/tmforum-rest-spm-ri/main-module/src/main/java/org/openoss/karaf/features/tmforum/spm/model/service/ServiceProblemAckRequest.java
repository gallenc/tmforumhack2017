package org.openoss.karaf.features.tmforum.spm.model.service;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.model.entity.ServiceProblem;
import org.openoss.karaf.features.tmforum.spm.model.entity.TrackingRecord;

@XmlRootElement
public class ServiceProblemAckRequest {

	List<ServiceProblem> problems=null;
	TrackingRecord trackingRecord=null;
	
}
