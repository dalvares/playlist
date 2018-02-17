package com.playlist.model;
import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

public class PrerollRef implements Serializable{
	private final static long serialVersionUID = 5624125234088045915L;

	public PrerollRef(){

	}

	@XmlElement
	@NotNull
	private String name;

	private PrerollRef(Builder builder) {
		name = builder.name;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(PrerollRef copy) {
		Builder builder = new Builder();
		builder.name = copy.name;
		return builder;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PrerollRef{");
		sb.append("name='").append(name).append('\'');
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PrerollRef that = (PrerollRef) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public static final class Builder {
		private String name;

		private Builder() {
		}

		public Builder name(String val) {
			name = val;
			return this;
		}

		public PrerollRef build() {
			return new PrerollRef(this);
		}
	}
}