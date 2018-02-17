package com.playlist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaylistResponse<T> implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	List<com.playlist.model.common.Error> errors;
	T result;

	public List<com.playlist.model.common.Error> getErrors() {
		return errors;
	}

	public T getResult() {
		return result;
	}

	public PlaylistResponse() {
		
	}
	
	private PlaylistResponse(Builder<T> builder) {
		this.result = builder.result;
		this.errors = builder.errors;
	}

	public static  <T> Builder<T> newBuilder() {
		return new Builder<T>();
	}

	public static<T> Builder<T> newBuilder(PlaylistResponse<T> copy) {
		Builder<T> builder = new Builder<T>();
		builder.result = copy.result;
		builder.errors = copy.errors;
		return builder;
	}

	@Override
	public String toString() {
		return "PlaylistResponse [errors=" + errors + ", result=" + result + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PlaylistResponse<T> pl = (PlaylistResponse) o;
		return Objects.equals(result, pl.result) && Objects.equals(errors, pl.errors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(result, errors);
	}

	public static final class Builder<T> {
		List<com.playlist.model.common.Error> errors = new ArrayList<>();
		T result;

		private Builder() {
		}

		public Builder<T>  addError(com.playlist.model.common.Error val) {
			errors.add(val);
			return this;
		}

		public Builder<T>  addError(List<com.playlist.model.common.Error> val) {
			errors.addAll(val);
			return this;
		}

		public Builder<T>  result(T val) {
			result = val;
			return this;
		}

		public PlaylistResponse<T> build() {
			return new PlaylistResponse<T>(this);
		}
	}

}
