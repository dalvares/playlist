package com.playlist.db.test;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class IOUtilsHelper {
	public static String getFileWithUtil(String fileName) {

		String result = "";

		ClassLoader classLoader = IOUtilsHelper.class.getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
