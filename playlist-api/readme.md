# ==================Playlist API =====================#

## Thoughts While building this module ##
This is a very clean cut API which will be used by other consumers who would require to invoke the service. Currently Both Read and Ingestion APIs are defined in same service.

In a real world problem Read and Ingestion APIs would be defined as 2 separate services in 2 different maven modules, currently they share same module playlist-api. 

The API is clean with limited dependencies on 3rd party jars, except spring core classes.

This Architecture helps reduce jar conflicts on the consumer side due to 3rd party jars clashing.

## APIs ##


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
	 * Get playlist data given contentName and countryCode.
	 * If no record is found return message like <p>
	 * (No legal playlist possible because the Pre-Roll Video isn't compatible with the aspect ratio of the Content Video for the US) <p>
	 * If content name is not found then return Not Found message with status as 404 
	 * If timeout occurs return a timeout message with status as 408
	 * @param contentName
	 * @param countryCode
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/playlist")
	ResponseEntity<PlaylistResponse<List<List<String>>>> getPlayList(@RequestParam("contentName") String contentName,
			@RequestParam("countryCode") String countryCode);
}
