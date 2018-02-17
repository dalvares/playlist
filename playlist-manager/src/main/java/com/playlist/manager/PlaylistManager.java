package com.playlist.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.playlist.db.ContentRepository;
import com.playlist.db.PreRollRepository;
import com.playlist.manager.loaders.PreRollLoaderTask;
import com.playlist.model.Content;
import com.playlist.model.ContentMetaData;
import com.playlist.model.Preroll;
import com.playlist.model.PrerollRef;
import com.playlist.model.VideoEligibility;
import com.playlist.model.exception.EligibilityMisMatchException;
import com.playlist.model.exception.NotFoundException;
import com.playlist.model.exception.PlaylistSystemException;
import com.playlist.model.exception.TimeOutException;

@Component
public class PlaylistManager {
	@Resource
	ContentRepository contentRepository;

	@Resource
	PreRollRepository preRollRepository;

	ExecutorService executor = Executors.newFixedThreadPool(2); // 2 Threads in pool, In real world this is a
																// configurable parameter which needs to come from a
																// configuration repository

	private static final Integer TIMEOUT_IN_MILLIS = 20; // In real world this is a configurable parameter which needs
															// to come from a configuration repository

	/**
	 * This method will compare request objects with db objects (for both content
	 * and preroll elements) and will filter the request objects whose state is same
	 * as db, filtered using <tt> Set.removeAll </tt> function
	 * <p>
	 * This method will remove keys that exist in db but do not exist in request
	 * <p>
	 * This method will save Elements that are eligible for update and will new
	 * elements to be inserted
	 * 
	 * Note for this problem <tt>sourcemodifiedtimestamp</tt> version is not
	 * maintained. This is important to prevent stale upsert requests from being
	 * processed.
	 * <p>
	 * But for this problem we are assuming there will be no failures or lag in
	 * requests
	 **/

	public void save(ContentMetaData metaData) {

		Set<Content> contentSetFromRequest = metaData.getContent();

		Set<Preroll> preRollFromRequest = metaData.getPreroll();

		// creates a set of preroll keys from preroll object in request
		Set<String> prerollIdsFromRequest = metaData.getPreroll() != null
				? metaData.getPreroll().parallelStream().map(e -> e.getName()).collect(Collectors.toSet())
				: Collections.emptySet();

		// creates a set of content keys from content object in request
		Set<String> contentIdsFromRequest = metaData.getContent() != null
				? metaData.getContent().parallelStream().map(e -> e.getName()).collect(Collectors.toSet())
				: Collections.emptySet();

		// filter data that has not changed. See method documentation for more details
		filterUnchangedRowsFromRequest(contentSetFromRequest, preRollFromRequest);

		// since content has a dependency on preroll , hence we save preroll first
		if (!metaData.getPreroll().isEmpty()) {
			preRollRepository.save(metaData.getPreroll());
		}

		// save content data
		if (!metaData.getContent().isEmpty())
			contentRepository.save(metaData.getContent());

		// This will remove preroll elements which do not exist in update request
		removePrerollElements(prerollIdsFromRequest, preRollRepository.getPrerollIds());

		// This will remove content elements which do not exist in update request
		removeContentElements(contentIdsFromRequest, contentRepository.getContentIds());

	}

	private void removeContentElements(Set<String> contentIdsFromRequest, Set<String> contentIdsFromDb) {
		// this is the set of contents that need to be deleted from DB
		contentIdsFromDb.removeAll(contentIdsFromRequest);

		if (!contentIdsFromDb.isEmpty())
			contentRepository.remove(contentIdsFromDb);
	}

	private void removePrerollElements(Set<String> prerollIdsFromRequest, Set<String> prerollIdsFromDb) {
		// this is the set of prerolls that need to be deleted from DB
		prerollIdsFromDb.removeAll(prerollIdsFromRequest);
		if (!prerollIdsFromDb.isEmpty())
			preRollRepository.remove(prerollIdsFromDb);
	}

	/**
	 * 
	 * The method <tt>filterUnchangedRows</tt> will filter content and preroll
	 * objects from request which have not changed since the last update.
	 * <p>
	 * 
	 * Since we are doing a full replace of a very large file the aim is to reduce
	 * the writes to database and only update the database if the object has changed
	 * else ignore the write to db
	 * 
	 * @param contentSetFromRequest
	 * @param preRollFromRequest
	 */
	private void filterUnchangedRowsFromRequest(Set<Content> contentSetFromRequest, Set<Preroll> preRollFromRequest) {
		// filter prerolls which are not updated
		preRollFromRequest.removeAll(preRollRepository.getPrerolls());

		// filter content which are not updated
		contentSetFromRequest.removeAll(contentRepository.getContents());
	}

	/**
	 * This method creates a ContentMetaData object by collecting the contents and
	 * preroll data from database
	 * 
	 * @return
	 */
	public ContentMetaData retrieveAllContentMetaData() {
		return ContentMetaData.newBuilder().content(contentRepository.getContents())
				.preroll(preRollRepository.getPrerolls()).build();
	}

	/**
	 * 
	 * 
	 * @param contentName
	 * @param countryCode
	 * @return
	 */
	public List<List<String>> getPlayList(String contentName, String countryCode) {

		if (contentRepository.getContent(contentName).isPresent()) {
			Content content = contentRepository.getContent(contentName).get();
			if ((content.getPreroll() == null || content.getPreroll().isEmpty())
					&& !preRollRepository.containsAllPrerolls(content.getPreroll())) {
				throw new EligibilityMisMatchException(
						"No legal playlist possible because the Pre-Roll Video isn't compatible with the aspect ratio of the Content Video for the"
								+ countryCode);
			}
			Map<VideoEligibility, String> contentVideoMap = contentRepository.getVideoAttributeMapByName(contentName);
			Set<VideoEligibility> contentVideoEligibilitySet = contentVideoMap.keySet().stream()
					.filter(v -> countryCode.equals(v.getCountry())).collect(Collectors.toSet());

			List<Map<VideoEligibility, String>> preRollVideoMapList = new ArrayList<>();

			// As per request from Igor , this code is use to demonstrate use of concurrency
			int PREROLL_SIZE = content.getPreroll().size();
			CountDownLatch latch = new CountDownLatch(PREROLL_SIZE); // coundown from 3 to 0
			List<Future<Optional<Map<VideoEligibility, String>>>> futureList = new ArrayList<>();
			for (PrerollRef pr : content.getPreroll()) {
				futureList.add(executor.submit(new PreRollLoaderTask(preRollRepository, pr.getName(), latch)));
			}
			try {
				boolean hasNotTimedOut = latch.await(TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS); // wait until latch counted down to 0
				if(!hasNotTimedOut) {
					throw new TimeOutException("Time out occured while loading preroll data ");
				}
			} catch (InterruptedException e) {
				throw new TimeOutException("Time out occured while loading preroll data " + e.getMessage());
			}

			for (Future<Optional<Map<VideoEligibility, String>>> preRollFuture : futureList) {
				try {
					Optional<Map<VideoEligibility, String>> res = preRollFuture.get();
					if (res.isPresent()) {
						Map<VideoEligibility, String> preRollVideoMap = res.get();
						preRollVideoMapList.add(preRollVideoMap);
						Set<VideoEligibility> prerollVideoEligibilitySet = preRollVideoMap.keySet();
						contentVideoEligibilitySet.retainAll(prerollVideoEligibilitySet);
					} else {
						// In line 162 above we have already verified that preroll data exists in DB ,
						// If missing then there is some issue so throw back an exception
						throw new PlaylistSystemException("Failed loading preroll data");
					}

				} catch (InterruptedException | ExecutionException e) {
					throw new PlaylistSystemException("Future task execution failure " + e.getMessage());
				}
			}

			/*
			 * 
			 * 
			 * Map<VideoEligibility, String> preRollVideoMap = preRollRepository
			 * .getVideoAttributeMapByName(preroll.getName());
			 * preRollVideoMapList.add(preRollVideoMap); Set<VideoEligibility>
			 * prerollVideoEligibilitySet = preRollVideoMap.keySet();
			 * contentVideoEligibilitySet.retainAll(prerollVideoEligibilitySet);
			 * 
			 */
			if (contentVideoEligibilitySet.isEmpty()) {
				throw new EligibilityMisMatchException(
						"No legal playlist possible because the Pre-Roll Video isn't compatible with the aspect ratio of the Content Video for the"
								+ countryCode);
			}

			List<List<String>> resultList = new ArrayList<>();
			for (VideoEligibility ve : contentVideoEligibilitySet) {
				List<String> videos = new ArrayList<>();
				String contentVideoName = contentVideoMap.get(ve);
				for (Map<VideoEligibility, String> prerollVideoMap : preRollVideoMapList) {
					if (prerollVideoMap.containsKey(ve)) {
						videos.add(prerollVideoMap.get(ve));
					}
				}
				videos.add(contentVideoName);
				resultList.add(videos);
			}
			return resultList;
		} else {
			throw new NotFoundException("No Content Found");
		}
	}

}
