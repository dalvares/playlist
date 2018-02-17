package com.playlist.model.common;

import com.playlist.model.exception.PlaylistCustomException;

public class ErrorUtil {

	public static com.playlist.model.common.Error createError(Exception ex) {

		if (ex instanceof PlaylistCustomException) {
			PlaylistCustomException p = (PlaylistCustomException) ex;
			return new com.playlist.model.common.Error(p.getCode().name(), p.getDescription(), p.getCategory());
		} else {
			return new com.playlist.model.common.Error(ErrorCode.UNKNOW_ERROR.name(), ex.getMessage(),
					ErrorCategory.SYSTEM);
		}
	}

}
