package com.playlist.model.exception;

import org.springframework.http.HttpStatus;

import com.playlist.model.common.ErrorCategory;
import static com.playlist.model.common.ErrorCode.DATA_NOT_FOUND;

public class NotFoundException extends PlaylistCustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String message) {
		super(DATA_NOT_FOUND,message,ErrorCategory.DATA, HttpStatus.NOT_FOUND);
	}
	
	

}