package org.opennms.plugins.graphml.client;

public class GraphmlClientException extends Exception {

	private static final long serialVersionUID = 6756295183463001693L;

	public GraphmlClientException(String errorstr) {
		super(errorstr);
	}

	public GraphmlClientException(String errorstr, Exception e) {
		super(errorstr,  e); 
	}

}
