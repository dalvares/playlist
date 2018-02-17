package com.playlist.db;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.playlist.model.Content;
import com.playlist.model.Video;
import com.playlist.model.VideoAttributes;
import com.playlist.model.VideoEligibility;

@Repository
public class ContentRepository {
	ConcurrentHashMap<String, Content> contentMapByContentId = new ConcurrentHashMap<>();

	// this may will be useful when we want to compare video attributes from
	// different content/preroll
	ConcurrentHashMap<String, Map<VideoEligibility, String>> contentToVideoAttributeMap = new ConcurrentHashMap<>();

	/**
	 * Since this set is large , we will process the set in parallel
	 * 
	 * @param data
	 */
	public void save(Set<Content> data) {
		data.parallelStream().forEach(content -> {
			contentMapByContentId.put(content.getName(), content);
			// This will reset the video map for a content name as key
			contentToVideoAttributeMap.put(content.getName(), new HashMap<>());
			// this will recompute all the video attributes for a content name as key
			content.getVideos().forEach(video -> {
				mapOfVideoEligibilitysByVideoName(content.getName(), video);
			});
		});
	}

	/**
	 * Since content set is unique we can delete entries in parallel
	 * 
	 * @param contentIdsFromDb
	 */
	public void remove(Set<String> contentIdsFromDb) {
		contentIdsFromDb.parallelStream().forEach(content -> {
			contentMapByContentId.remove(content);
			contentToVideoAttributeMap.remove(content);
		});
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getContentIds() {
		return new HashSet<>(contentMapByContentId.keySet());
	}

	/**
	 * returns a set of immutable objects
	 * 
	 * @return
	 */
	public Set<Content> getContents() {
		return new HashSet<>(contentMapByContentId.values());
	}

	/**
	 * Content is a immutable class , hence can be returned safely
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Content> getContent(String name) {
		return Optional.ofNullable(contentMapByContentId.get(name));
	}



	public Map<VideoEligibility, String> getVideoAttributeMapByName(String name) {
		Set<Entry<VideoEligibility, String>> entrySet = contentToVideoAttributeMap.get(name).entrySet();

		Map<VideoEligibility, String> VideoEligibilityToVideoMap = new HashMap<>();
		for (Entry<VideoEligibility, String> entry : entrySet) {
			VideoEligibilityToVideoMap.put(entry.getKey(), entry.getValue());
		}
		return VideoEligibilityToVideoMap;
	}

	private void mapOfVideoEligibilitysByVideoName(String name, Video video) {
		for (String country : video.getAttributes().getCountries()) {
			VideoAttributes videoAtt = video.getAttributes();
			VideoEligibility ve = VideoEligibility.newBuilder().language(videoAtt.getLanguage()).aspect(videoAtt.getAspect())
					.country(country).build();
			contentToVideoAttributeMap.get(name).put(ve, video.getName());
		}
	}
}
