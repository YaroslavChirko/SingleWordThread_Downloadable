package com.singleword.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.singleword.db.dao.SingleWordMessageRepository;
import com.singleword.db.entity.SingleWordMessage;

@Controller
public class IndexController {

	@Autowired
	SingleWordMessageRepository messageRepository;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	private String getIndex(HttpServletRequest req) {
		req.setAttribute("messages", messageRepository.getAllMessages());
		return "index";
	}
	
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	private void postIndex(@RequestParam("message") String word) {
		SingleWordMessage message = new SingleWordMessage(word); 
		
		messageRepository.addMessage(message);
	}
	
}
