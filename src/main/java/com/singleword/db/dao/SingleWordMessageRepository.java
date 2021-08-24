package com.singleword.db.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.singleword.db.entity.SingleWordMessage;

@Repository
public class SingleWordMessageRepository {
	private JdbcTemplate connection;
	
	
	//change to some message interface to be able to use other message types later on
	private  RowMapper<SingleWordMessage> messageMapper = (ResultSet rs, int rowNum) -> {
		SingleWordMessage message = new SingleWordMessage();
		message.setWord(rs.getString("word"));
		
		//change later as the time won`t be proper
		message.setSentAt(rs.getDate("sent_at").toLocalDate().atStartOfDay());
		message.setRandomName(rs.getString("username"));
		return message;
	};
	
	@Autowired
	public SingleWordMessageRepository(DataSource source) {
			this.connection = new JdbcTemplate(source);
			createTableNotExists();
	}
	
	// changed to method since initialization blocks are called before the constructor
	 private void createTableNotExists(){
		String createQuery = "CREATE TABLE IF NOT EXISTS thread_messages ("
				+ "id SERIAL UNIQUE,"
				+ "word VARCHAR (50),"
				+ "sent_at DATE,"
				+ "username VARCHAR(10));";
		connection.execute(createQuery);
	}
	 
	/**
	 * this method is used to persist messages, currently only saves it to single table, 
	 * the one-to-many relation with threads will be implemented later
	 * regex checks for characters used, could check using bean validation, but since only one param is passed
	 * from the post it will only check that the message word is right
	 */
	public void addMessage(SingleWordMessage message) {
		
		if(message == null)throw new IllegalArgumentException("no message passed");
		if(message.getWord()==null ||message.getWord().length()>50)
			throw new IllegalArgumentException("no message content or contents are too big");
		if(message.getRandomName()==null ||message.getRandomName().length()>10
				||message.getRandomName().length()<3) throw new IllegalArgumentException("invalid name");
		if(message.getWord().matches("[A-Za-z0-9\'\"\\`?!\\-*\\(\\)\\[\\]\\{\\}/]{1,50}")) {
			String saveQuery = "INSERT INTO thread_messages (word,sent_at,username) VALUES (?,?,?)";
			connection.update(saveQuery,message.getWord(),message.getSentAt(),message.getRandomName());			
		}else {
			throw new IllegalArgumentException("word contains illegal characters, legal are:"
					+ "\n digits, letters, braces, question mark, quotes, double quotes, minus, and exclamation mark");
		}
	}
	
	/**
	 * used to get all messages, should later make it to retrieve only messages from specific thread
	 */
	public List<SingleWordMessage> getAllMessages(){
		String getQuery = "SELECT * FROM thread_messages";
		return connection.query(getQuery,messageMapper);
	}
	
}
