package org.openoss.karaf.features.tmforum.spm.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.openoss.karaf.features.tmforum.spm.model.external.User;

@XmlRootElement
public class Comment {

	private String href = null;

	private String id = null;

	private User user = null;

	private Date time = null;

	private String systemld = null;

}
