package com.singleword.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.singleword.download.MessageThreadDownloadService;

@Controller
public class DownloadController {
	
	@Autowired
	MessageThreadDownloadService downloadService;

	@RequestMapping(value="/download", method=RequestMethod.GET)
	private ResponseEntity<Resource> getDownload(HttpServletRequest request) {
		downloadService.prepareAllMessages("main");
		System.out.println("download completed");
		try {
			Resource file = downloadService.getMessageFile("main");
			String type = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
			
			if(type!=null) {
				return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(type))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"main.txt\"")
						.body(file);
			}else {
				return null;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
