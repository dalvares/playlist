package com.playlist.db.test;

import static com.playlist.db.test.IOUtilsHelper.getFileWithUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playlist.db.ContentRepository;
import com.playlist.model.Content;
import com.playlist.model.ContentMetaData;

public class ContentRepositoryTest {
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
		ContentRepository repo = new ContentRepository();
		repo.save(data1.getContent());
		repo.save(data2.getContent());
		
		Set<Content> mergedContent =   new HashSet<>(data1.getContent());
		mergedContent.addAll(data2.getContent());
		
		Assert.assertEquals("Both the content sets are not equal",mergedContent,repo.getContents());	
	}

	
}
