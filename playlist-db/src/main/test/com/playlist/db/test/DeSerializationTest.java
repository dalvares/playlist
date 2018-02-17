package com.playlist.db.test;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playlist.model.Content;
import com.playlist.model.ContentMetaData;
import com.playlist.model.Video;

public class DeSerializationTest {
	ObjectMapper mapper = new ObjectMapper();

	@org.junit.Test
	public void jsonToJava() {
		String content = getFileWithUtil("content-list.json");

		try {
			ContentMetaData val = mapper.readValue(content, ContentMetaData.class);
			org.junit.Assert.assertNotNull("This object cannot be null", val);
			Content first = val.getContent().iterator().next();
			org.junit.Assert.assertEquals("Content name incorrect ",first.getName(), "MI3");

			Video firstVideo = first.getVideos().iterator().next();
			org.junit.Assert.assertEquals(" The first Video name is expected to be V1", firstVideo.getName(), "V1");

			String countries = firstVideo.getAttributes().getCountries().iterator().next();
			org.junit.Assert.assertEquals(" The first Country value is expected to be US", countries, "US");

		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}

	}

	private static String getFileWithUtil(String fileName) {

		String result = "";

		ClassLoader classLoader = DeSerializationTest.class.getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
