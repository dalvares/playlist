package com.playlist.model;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "countries", "language","aspect"})
public class VideoAttributes implements Serializable,Cloneable {

	private final static long serialVersionUID = -8389696468193485667L;

	public VideoAttributes() {
		// TODO Auto-generated constructor stub
	}
	
	/* 
	 * 
	 * Using HashSet as it maintains the insertion order and returns a unique set.
	 * For this problem it is assumed that all the countries  elements  in a set will be unique
	 * Time complexity to retrieve/adding from Linked Hash Set is O(1) in case of lookup  and also maintains order
	 * 
	 * */
	
	@XmlElement
	@NotNull
	//@JsonDeserialize(as = LinkedHashSet.class)
	/**
	 * There are a total of 195 countries in the world so setting max size to 195
	 */
	@Size(min=1,max=195)
	private Set<String> countries;
	
	@XmlElement
	@NotNull
	private String language;
	
	@XmlElement
	@NotNull
	private String aspect;

	private VideoAttributes(Builder builder) {
		countries = builder.countries;
		language = builder.language;
		aspect = builder.aspect;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(VideoAttributes copy) {
		Builder builder = new Builder();
		builder.countries = copy.countries;
		builder.language = copy.language;
		builder.aspect = copy.aspect;
		return builder;
	}


	public Set<String> getCountries() {
		return countries;
	}

	public String getLanguage() {
		return language;
	}

	public String getAspect() {
		return aspect;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VideoAttributes that = (VideoAttributes) o;
		return Objects.equals(countries, that.countries) &&
				Objects.equals(language, that.language) &&
				Objects.equals(aspect, that.aspect);
	}

	@Override
	public int hashCode() {
		return Objects.hash(countries, language, aspect);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("VideoAttributes{");
		sb.append("countries=").append(countries);
		sb.append(", language='").append(language).append('\'');
		sb.append(", aspect='").append(aspect).append('\'');
		sb.append('}');
		return sb.toString();
	}


	public static final class Builder {
		private Set<String> countries;
		private String language;
		private String aspect;

		private Builder() {
		}

		public Builder countries(Set<String> val) {
			countries = val;
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

		public VideoAttributes build() {
			return new VideoAttributes(this);
		}
	}
}

