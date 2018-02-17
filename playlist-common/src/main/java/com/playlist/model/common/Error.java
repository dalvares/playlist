package com.playlist.model.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "code", "description", "category" })
@XmlRootElement(name = "Error")
public class Error implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "code", required = true)
	private String code;

	@XmlElement(name = "description", required = false)
	private String description;

	@XmlElement(name = "category", required = false)
	private ErrorCategory category = ErrorCategory.REQUEST;

	public Error() {
		
	}
	
	public Error(String code, String description, ErrorCategory category) {
		this.code = code;
		this.category = category;
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public ErrorCategory getCategory() {
		return category;
	}
	
	

}
