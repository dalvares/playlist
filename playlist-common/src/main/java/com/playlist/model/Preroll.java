package com.playlist.model;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "videos"})
public class Preroll implements Serializable {

	private final static long serialVersionUID = 5624115534088045915L;

	public  Preroll(){

	}
	/**
	 * 
	 */

	@XmlElement
	@NotNull
	private String name;

	/*
	 *
	 * Using Linked HashSet as it maintains the insertion order and returns a unique set.
	 * For this problem it is assumed that all the videos are unique
	 *
	 * */

	@XmlElement
	@NotNull
	@JsonDeserialize(as = LinkedHashSet.class)
	private Set<Video> videos;

	private Preroll(Builder builder) {
		name = builder.name;
		videos = builder.videos;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Preroll copy) {
		Builder builder = new Builder();
		builder.name = copy.name;
		builder.videos = copy.videos;
		return builder;
	}

	public String getName() {
		return name;
	}


	public Set<Video> getVideos() {
		return videos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Preroll preroll = (Preroll) o;
		return Objects.equals(name, preroll.name) &&
				Objects.equals(videos, preroll.videos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, videos);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Preroll{");
		sb.append("name='").append(name).append('\'');
		sb.append(", videos=").append(videos);
		sb.append('}');
		return sb.toString();
	}


	public static final class Builder {
		private String name;
		private Set<Video> videos;

		private Builder() {
		}

		public Builder name(String val) {
			name = val;
			return this;
		}

		public Builder videos(Set<Video> val) {
			videos = val;
			return this;
		}

		public Preroll build() {
			return new Preroll(this);
		}
	}
}