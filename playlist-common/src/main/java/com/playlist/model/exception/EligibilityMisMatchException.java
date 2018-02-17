package com.playlist.model.exception;

import org.springframework.http.HttpStatus;

import com.playlist.model.common.ErrorCategory;
import static com.playlist.model.common.ErrorCode.ELIGIBILITY_MISMATCH;

public class EligibilityMisMatchException extends PlaylistCustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EligibilityMisMatchException(String message) {
		super(ELIGIBILITY_MISMATCH, message, ErrorCategory.DATA, HttpStatus.BAD_REQUEST);
	}

}
