package com.learning.readers.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	private static final String ABS_PATH = "E:/MScIT/Sem1/Project/readers/readers/src/main/webapp";

	public static void uploadFile(HttpServletRequest req, MultipartFile file, String destinationPath, String fileName) {
		
		String realPath = req.getServletContext().getRealPath(destinationPath);
		System.out.println(destinationPath + " => " + realPath);
		
		File destinationDir = new File(realPath);
		if (!destinationDir.exists()) {
			destinationDir.mkdirs();
		}
		
		destinationDir = new File(ABS_PATH + destinationPath + File.separator);
		if (!destinationDir.exists()) {
			destinationDir.mkdirs();
		}
		
		try {
			//Upload to server
			file.transferTo(new File(realPath + File.separator + fileName));
			
			//TODO: Project directory upload
			file.transferTo(new File(ABS_PATH + destinationPath + File.separator + fileName));
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
