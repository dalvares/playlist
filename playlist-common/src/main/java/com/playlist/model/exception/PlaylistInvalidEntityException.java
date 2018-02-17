package com.playlist.model.exception;

import java.util.ArrayList;
import java.util.List;

public class PlaylistInvalidEntityException extends RuntimeException {

	List<com.playlist.model.common.Error> errors;

	public List<com.playlist.model.common.Error> getErrors() {
		return errors;
	}

	private PlaylistInvalidEntityException() {

	}

	private PlaylistInvalidEntityException(Builder builder) {
		this.errors = builder.errors;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(PlaylistInvalidEntityException copy) {
		Builder builder = new Builder();
		builder.errors = copy.errors;
		return builder;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final class Builder {
		List<com.playlist.model.common.Error> errors = new ArrayList<>();

		private Builder() {
		}

		public Builder addError(com.playlist.model.common.Error val) {
			errors.add(val);
			return this;
		}

		public Builder addError(List<com.playlist.model.common.Error> val) {
			errors.addAll(val);
			return this;
		}

		public PlaylistInvalidEntityException build() {
			return new PlaylistInvalidEntityException(this);
		}
	}

}
