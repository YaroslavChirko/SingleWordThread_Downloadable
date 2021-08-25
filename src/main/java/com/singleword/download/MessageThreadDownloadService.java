package com.singleword.download;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.singleword.db.dao.SingleWordMessageRepository;
import com.singleword.db.entity.SingleWordMessage;

/**
 * 
 * @author Erick
 *
 *Class to download messages,<br>
 * other implementation can be added using selenium webDriver<br> 
 * to read all messages on the page and write them,<br> 
 * should also provide servlet mapping to download the file rather than just leave it on server
 *
 */

@Service
public class MessageThreadDownloadService {

	private SingleWordMessageRepository messageRepository;
	
	@Autowired
	public MessageThreadDownloadService(SingleWordMessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	/**
	 * used to put all messages in file, filename could be arbitrary but for now it`ll be thread name
	 */
	public void prepareAllMessages(String filename) {
		List<SingleWordMessage> messages = messageRepository.getAllMessages(messageRepository.getThreadIdForName(filename));
		if(messages == null || messages.isEmpty()) {
			return;
		}
		
		try {
			//for now it clears the file on each call, should optimize it by looking at the last submitted message(time) and time of file creation 
			if(Files.exists(Paths.get("./"+filename+".txt"), LinkOption.NOFOLLOW_LINKS)){
				Files.delete(Paths.get("./"+filename+".txt"));
			}
			//changed to buffered output stream since it can be easily read in text form
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("./"+filename+".txt"),true));
			
			for(SingleWordMessage message : messages) {
				out.write(message.toString().getBytes());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public Resource getMessageFile(String filename) throws FileNotFoundException {
		try {
			Resource result = new UrlResource(Paths.get("./"+filename+".txt").toUri());
			if(result.exists())return result;
			else throw new FileNotFoundException("no file was found");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FileNotFoundException("no file was found");
		} 
	}
	
}
