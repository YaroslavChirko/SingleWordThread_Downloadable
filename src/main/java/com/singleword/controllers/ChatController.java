package com.singleword.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.singleword.db.dao.SingleWordMessageRepository;
import com.singleword.db.entity.SingleWordMessage;

@Controller
public class ChatController {
	@Autowired
	SingleWordMessageRepository messageRepository;
	
	@RequestMapping(value="chat/{threadName}", method = RequestMethod.GET)
	private String getChatMessages(@PathVariable("threadName") String threadName,
			HttpServletRequest request){
		request.setAttribute("messages", messageRepository.getAllMessages(
				messageRepository.getThreadIdForName(threadName)));
		request.setAttribute("threadName", threadName);
		return "chat";
	}
	
	@RequestMapping(value="chat/{threadName}",method=RequestMethod.POST)
	private void addMessage(@PathVariable("threadName") String threadName, 
			@RequestParam("message") String word) {
		SingleWordMessage message = new SingleWordMessage(word); 
		
		messageRepository.addMessage(message,threadName);
	}
	
}
