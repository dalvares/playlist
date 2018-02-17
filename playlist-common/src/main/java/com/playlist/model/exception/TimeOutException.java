package com.playlist.model.exception;

import org.springframework.http.HttpStatus;


import com.playlist.model.common.ErrorCategory;
import static com.playlist.model.common.ErrorCode.READ_TIME_OUT;;

public class TimeOutException extends PlaylistCustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeOutException(String message) {
		super(READ_TIME_OUT, message, ErrorCategory.SYSTEM, HttpStatus.REQUEST_TIMEOUT);
	}

}
