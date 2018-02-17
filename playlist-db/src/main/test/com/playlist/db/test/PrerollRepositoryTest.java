package com.playlist.db.test;

import static com.playlist.db.test.IOUtilsHelper.getFileWithUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playlist.db.PreRollRepository;
import com.playlist.model.ContentMetaData;
import com.playlist.model.Preroll;

public class PrerollRepositoryTest {
	ObjectMapper mapper = new ObjectMapper();
	ContentMetaData data1;
	ContentMetaData data2;

	@Before
	public void loadLargeFile() {
		String content1 = getFileWithUtil("content-list1.json");
		String content2 = getFileWithUtil("content-list2.json");
		try {
			data1 = mapper.readValue(content1, ContentMetaData.class);
			data2 = mapper.readValue(content2, ContentMetaData.class);
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void equalTest() {
		PreRollRepository repo = new PreRollRepository();
		repo.save(data1.getPreroll());
		repo.save(data2.getPreroll());
		
		Set<Preroll> mergedPreroll =   new HashSet<>(data1.getPreroll());
		mergedPreroll.addAll(data2.getPreroll());
		
		Assert.assertEquals("Both the content sets are not equal",mergedPreroll,repo.getPrerolls());	
	}

	
}
