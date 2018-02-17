package com.playlist.model.exception;

import org.springframework.http.HttpStatus;

import com.playlist.model.common.ErrorCategory;
import  static com.playlist.model.common.ErrorCode.SYSTEM_EXCEPTION;

public class PlaylistSystemException extends PlaylistCustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlaylistSystemException(String message) {
		super(SYSTEM_EXCEPTION,message,ErrorCategory.SYSTEM,HttpStatus.BAD_REQUEST);
	}
	
	

}
