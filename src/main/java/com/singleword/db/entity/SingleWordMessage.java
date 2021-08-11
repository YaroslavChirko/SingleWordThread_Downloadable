package com.singleword.db.entity;

import java.time.LocalDateTime;

import com.singleword.functions.RandomSingleWordName;



public class SingleWordMessage {
	//should add bean validation later
	private String word;
	private LocalDateTime sentAt;
	private String randomName;
	
	public SingleWordMessage() {}
	
	public SingleWordMessage(String word) {
		this.word = word;
		this.sentAt = LocalDateTime.now();
		//call to random name generator, wonder if it`ll produce any legitimate names
		this.randomName = RandomSingleWordName.getInstance().generateName();
	}
	
	public synchronized String getWord() {
		return word;
	}
	public synchronized void setWord(String word) {
		this.word = word;
	}
	public synchronized LocalDateTime getSentAt() {
		return sentAt;
	}
	public synchronized void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	public synchronized String getRandomName() {
		return randomName;
	}
	public synchronized void setRandomName(String randomName) {
		this.randomName = randomName;
	}
	
	
	
}
