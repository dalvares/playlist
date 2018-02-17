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

import com.playlist.model.Preroll;
import com.playlist.model.PrerollRef;
import com.playlist.model.Video;
import com.playlist.model.VideoAttributes;
import com.playlist.model.VideoEligibility;

@Repository
public class PreRollRepository {

	ConcurrentHashMap<String, Preroll> prerollMapByPrerollId = new ConcurrentHashMap<>();

	/*
	 * One of the requirements states that each content or pre-roll can have
	 * multiple videos attached to it. Each attached video is uniquely tagged with a
	 * language, a list of countries and an aspect ratio.
	 */

	ConcurrentHashMap<String, HashMap<VideoEligibility, String>> prerollToVideoEligibilityMap = new ConcurrentHashMap<>();

	/**
	 * 
	 * Save the set of Preroll objects to DB that need to be updated/inserted
	 * 
	 * @param data
	 */
	public void save(Set<Preroll> data) {
		data.parallelStream().forEach(preroll -> {
			prerollMapByPrerollId.put(preroll.getName(), preroll);
			// This will reset the video map for a preroll name as key
			prerollToVideoEligibilityMap.put(preroll.getName(), new HashMap<>());
			// this will recompute all the video attributes for a preroll
			preroll.getVideos().forEach(video -> {
				mapOfVideoEligibilitysByVideoName(preroll.getName(), video);
			});

		});
	}

	/**
	 * Create a Map of <tt>VideoEligibility</tt> to Video Id 
	 * 
	 * The internal map Create a map of Video Elements to Video ID 
	 * @param name
	 * @param video
	 */
	private void mapOfVideoEligibilitysByVideoName(String name, Video video) {
		for (String country : video.getAttributes().getCountries()) {
			VideoAttributes videoAtt = video.getAttributes();
			VideoEligibility ve = VideoEligibility.newBuilder().language(videoAtt.getLanguage()).aspect(videoAtt.getAspect())
					.country(country).build();
			prerollToVideoEligibilityMap.get(name).put(ve, video.getName());
		}
	}

	/**
	 * Since preroll set is unique we can delete entries in parallel
	 * 
	 * @param prerollIdsFromDb
	 */
	public void remove(Set<String> prerollIdsFromDb) {
		prerollIdsFromDb.parallelStream().forEach(preroll -> {
			prerollMapByPrerollId.remove(preroll);
		});
	}

	/**
	 * 
	 * @return
	 */
	public Set<String> getPrerollIds() {
		return new HashSet<>(prerollMapByPrerollId.keySet());
	}

	/**
	 * 
	 * @return
	 */
	public Set<Preroll> getPrerolls() {
		return new HashSet<>(prerollMapByPrerollId.values());
	}

	/**
	 * Preroll is a immutable class , hence can be returned safely
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Preroll> getPreroll(String name) {
		return Optional.ofNullable(prerollMapByPrerollId.get(name));
	}

	

	public boolean containsAllPrerolls(Set<PrerollRef> prerollRefIds) {
		for (PrerollRef preroll : prerollRefIds) {
			if (!prerollMapByPrerollId.containsKey(preroll.getName())) {
				return false;
			}
		}
		return true;
	}

	public Map<VideoEligibility, String> getVideoAttributeMapByName(String name) {
		Set<Entry<VideoEligibility, String>> entrySet = prerollToVideoEligibilityMap.get(name).entrySet();

		Map<VideoEligibility, String> VideoEligibilityToVideoMap = new HashMap<>();
		for (Entry<VideoEligibility, String> entry : entrySet) {
			VideoEligibilityToVideoMap.put(entry.getKey(), entry.getValue());
		}
		return VideoEligibilityToVideoMap;
	}

}
