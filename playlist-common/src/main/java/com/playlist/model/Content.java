package com.playlist.model;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "preroll", "videos"})
public final class Content  implements Serializable {
	private final static long serialVersionUID = 1624515534088095916L;

	public  Content(){

	}
	
	/*
	 * 
	 * Using Linked HashSet as it maintains the insertion order and returns a unique set
	 * The order in which the playlist will play preroll videos  is same as the order of prerolls in this set.
	 * Assuming there can be max 10 preroll references per content
	 * */
	//@JsonDeserialize(as = LinkedHashSet.class) 
	@Valid
	@NotNull
	@Size(min=0,max=10)
    private Set<PrerollRef> preroll;

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
	//@JsonDeserialize(as = LinkedHashSet.class)
	@Valid
	private Set<Video> videos;

	private Content(Builder builder) {
		preroll = builder.preroll;
		name = builder.name;
		videos = builder.videos;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Content copy) {
		Builder builder = new Builder();
		builder.preroll = copy.preroll;
		builder.name = copy.name;
		builder.videos = copy.videos;
		return builder;
	}

	public Set<PrerollRef> getPreroll() {
		return preroll;
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
		Content content = (Content) o;
		return Objects.equals(preroll, content.preroll) &&
				Objects.equals(name, content.name) &&
				Objects.equals(videos, content.videos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(preroll, name, videos);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Content{");
		sb.append("preroll=").append(preroll);
		sb.append(", name='").append(name).append('\'');
		sb.append(", videos=").append(videos);
		sb.append('}');
		return sb.toString();
	}

	public static final class Builder {
		private Set<PrerollRef> preroll;
		private String name;
		private Set<Video> videos;

		private Builder() {
		}

		public Builder preroll(Set<PrerollRef> val) {
			preroll = val;
			return this;
		}

		public Builder name(String val) {
			name = val;
			return this;
		}

		public Builder videos(Set<Video> val) {
			videos = val;
			return this;
		}

		public Content build() {
			return new Content(this);
		}
	}
}