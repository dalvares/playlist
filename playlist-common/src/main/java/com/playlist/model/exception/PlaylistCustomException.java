package com.playlist.model.exception;

import org.springframework.http.HttpStatus;

import com.playlist.model.common.ErrorCategory;
import com.playlist.model.common.ErrorCode;

public class PlaylistCustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	private ErrorCode code;
	private ErrorCategory category;
	private HttpStatus status;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(ErrorCode code) {
		this.code = code;
	}

	public ErrorCategory getCategory() {
		return category;
	}

	public void setCategory(ErrorCategory category) {
		this.category = category;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public PlaylistCustomException(ErrorCode code, String description, ErrorCategory category) {
		this(code, description, category, null);
	}

	public PlaylistCustomException(ErrorCode code, String description, ErrorCategory category, HttpStatus status) {
		super(description);
		this.description = description;
		this.code = code;
		this.category = category;
		this.status = status;
	}

}
