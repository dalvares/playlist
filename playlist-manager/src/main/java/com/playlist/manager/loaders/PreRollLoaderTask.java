package com.playlist.manager.loaders;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.playlist.db.PreRollRepository;
import com.playlist.model.VideoEligibility;

public class PreRollLoaderTask implements Callable<Optional<Map<VideoEligibility, String>>> {
	private CountDownLatch latch;
	private String preRollName;
	private PreRollRepository repo;

	public PreRollLoaderTask(PreRollRepository repo, String preRollName, CountDownLatch latch) {
		this.latch = latch;
		this.preRollName = preRollName;
		this.repo = repo;

	}

	@Override
	public Optional<Map<VideoEligibility, String>> call() throws Exception {

		Map<VideoEligibility, String> preRollVideoMap = repo.getVideoAttributeMapByName(preRollName);
		//Thread.sleep(200); for timeout test
		latch.countDown();
		return Optional.ofNullable(preRollVideoMap);
	}

}
