package org.openoss.karaf.features.tmforum.spm.impl.client.rest;

public class SPMClientException extends Exception {

	private static final long serialVersionUID = 6756295183463001693L;

	public SPMClientException(String errorstr) {
		super(errorstr);
	}

	public SPMClientException(String errorstr, Exception e) {
		super(errorstr,  e); 
	}

}
