package com.playlist.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.playlist.model.ContentMetaData;
import com.playlist.model.PlaylistResponse;

public interface PlaylistContentIngestionService {

	/**
	 * Assumptions:The use case this API needs to support is a full replace. 
	 * So we need to create/update existing content and preroll elements and delete the
	 * ones which are not in use.
	 * 
	 * @param contentMetaData
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/meta-data")
	ResponseEntity<Object> uploadContentMetaData(@RequestBody ContentMetaData contentMetaData);

	/**
	 * Although we do not have a use case of this API. I am implementing this functionality as it is useful in reviewing the state of the DB on nultiple updates
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/meta-data")
	ResponseEntity<PlaylistResponse<ContentMetaData>> retrieveAllContentMetaData();

	/**
	 * Get playlist information given contentName and countryCode.
	 * If no record is found return message like <p>
	 * (No legal playlist possible because the Pre-Roll Video isn't compatible with the aspect ratio of the Content Video for the US) <p>
	 * If content name is not found then return Not Found message with status as 404 
	 * @param contentName
	 * @param countryCode
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/playlist")
	ResponseEntity<PlaylistResponse<List<List<String>>>> getPlayList(@RequestParam("contentName") String contentName,
			@RequestParam("countryCode") String countryCode);
}
