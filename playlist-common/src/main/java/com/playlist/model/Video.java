package com.playlist.model;
import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "attributes"})
public class Video implements Serializable {
	private final static long serialVersionUID = 1320480702053122965L;

	public Video(){

	}

	@XmlElement
	@NotNull
	private String name;
	
	@XmlElement
	@NotNull
	@Valid
	private VideoAttributes attributes;
	

	private Video(Builder builder) {
		name = builder.name;
		attributes = builder.attributes;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Video copy) {
		Builder builder = new Builder();
		builder.name = copy.name;
		builder.attributes = copy.attributes;
		return builder;
	}

	public String getName() {
		return name;
	}

	public VideoAttributes getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Video{");
		sb.append("name='").append(name).append('\'');
		sb.append(", attributes=").append(attributes);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Video video = (Video) o;
		return Objects.equals(name, video.name) &&
				Objects.equals(attributes, video.attributes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, attributes);
	}

	public static final class Builder {
		private String name;
		private VideoAttributes attributes;

		private Builder() {
		}

		public Builder name(String val) {
			name = val;
			return this;
		}

		public Builder attributes(VideoAttributes val) {
			attributes = val;
			return this;
		}

		public Video build() {
			return new Video(this);
		}
	}
}