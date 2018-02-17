package com.playlist.model;

import java.util.Objects;

/**
 * Video Eligibility properties
 * 
 * @author dalvares
 *
 */
public class VideoEligibility {
	String language;
	String country;
	String aspect;

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public String getAspect() {
		return aspect;
	}

	private VideoEligibility(Builder builder) {
		this.country = builder.country;
		this.language = builder.language;
		this.aspect = builder.aspect;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(VideoEligibility copy) {
		Builder builder = new Builder();
		builder.country = copy.country;
		builder.language = copy.language;
		builder.aspect = copy.aspect;
		return builder;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VideoEligibility that = (VideoEligibility) o;
		return Objects.equals(language, that.language) && Objects.equals(country, that.country)
				&& Objects.equals(aspect, that.aspect);
	}

	@Override
	public int hashCode() {
		return Objects.hash(language, country, aspect);
	}

	@Override
	public String toString() {
		return "VideoEligibility [language=" + language + ", country=" + country + ", aspect=" + aspect + "]";
	}

	public static final class Builder {
		private String country;
		private String language;
		private String aspect;

		private Builder() {
		}

		public Builder country(String val) {
			country = val;
			return this;
		}

		public Builder language(String val) {
			language = val;
			return this;
		}

		public Builder aspect(String val) {
			aspect = val;
			return this;
		}

		public VideoEligibility build() {
			return new VideoEligibility(this);
		}
	}

}