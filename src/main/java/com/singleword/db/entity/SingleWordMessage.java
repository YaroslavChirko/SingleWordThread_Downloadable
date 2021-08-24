package com.singleword.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.singleword.functions.RandomSingleWordName;


/**
* class used to persist the messages,<br> it implements <code>Serializable</code>
* interface which enables us to use it along with  object input and output streams
*/
public class SingleWordMessage implements Serializable{
	
	private static final long serialVersionUID = -4754530363203707343L;
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
