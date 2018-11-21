package com.learning.readers.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

	public static File getResourceFile(String filePath) {
		File file = new File(Thread.currentThread().getContextClassLoader().getResource(filePath).getFile());
		return file;
	}
	
	public static String getResourceFileContent(String filePath) throws IOException {
		File resourceFile = getResourceFile(filePath);
		String content = new String(Files.readAllBytes(resourceFile.toPath()));
		
		return content;
	}
}
