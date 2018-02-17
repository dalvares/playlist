package com.playlist.model;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "content", "preroll"})
public class ContentMetaData implements Serializable {
	private final static long serialVersionUID = 5624515534088045916L;

	public ContentMetaData(){

	}

	/* 
	 * 
	 * Using Linked HashSet as it maintains the insertion order and returns a unique set.
	 * For this problem it is assumed that all the content elements  are unique.
	 * Time complexity to retrieve/adding from Linked Hash Set is O(1) in case of lookup  and also maintains order
	 * 
	 * */
	@NotNull
	@Valid
	private Set<Content> content;
	
	/* 
	 * 
	 * I want to have the ability to lookup preroll set from 
	 * Using Linked HashSet as it maintains the insertion order and returns a unique set.
	 * For this problem it is assumed that all the preroll elements  are unique.
	 * Time complexity to retrieve/adding from Linked Hash Set is O(1) in case of lookup  and also maintains order
	 * 
	 * */
	
	@NotNull
	@JsonDeserialize(as = LinkedHashSet.class)
	@Valid
	private Set<Preroll> preroll;

	public Set<Content> getContent() {
		return content;
	}

	public Set<Preroll> getPreroll() {
		return preroll;
	}

	private ContentMetaData(Builder builder) {
		content = builder.content;
		preroll = builder.preroll;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(ContentMetaData copy) {
		Builder builder = new Builder();
		builder.content = copy.content;
		builder.preroll = copy.preroll;
		return builder;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ContentMetaData that = (ContentMetaData) o;
		return Objects.equals(content, that.content) &&
				Objects.equals(preroll, that.preroll);
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, preroll);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("ContentMetaData{");
		sb.append("content=").append(content);
		sb.append(", preroll=").append(preroll);
		sb.append('}');
		return sb.toString();
	}


	public static final class Builder {
		private Set<Content> content;
		private Set<Preroll> preroll;

		private Builder() {
		}

		public Builder content(Set<Content> val) {
			content = val;
			return this;
		}

		public Builder preroll(Set<Preroll> val) {
			preroll = val;
			return this;
		}

		public ContentMetaData build() {
			return new ContentMetaData(this);
		}
	}
}