package com.singleword.db.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.singleword.db.entity.SingleWordMessage;
import com.singleword.db.entity.SingleWordThread;

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
	
	private RowMapper<SingleWordThread> threadMapper = (ResultSet rs, int rowNum) -> {
			SingleWordThread thread = new SingleWordThread();
			thread.setId(rs.getInt("id"));
			thread.setName(rs.getString("name"));
			return thread;
		};
	
	@Autowired
	public SingleWordMessageRepository(DataSource source) {
			this.connection = new JdbcTemplate(source);
			createThreadTableNotExists();
			createMessageTableNotExists();
			addThread("main");
	}
	
	// changed to method since initialization blocks are called before the constructor
	 private void createMessageTableNotExists(){
		String createQuery = "CREATE TABLE IF NOT EXISTS thread_messages ("
				+ "id SERIAL UNIQUE,"
				+ "word VARCHAR (50),"
				+ "sent_at DATE,"
				+ "username VARCHAR(10), "
				+ "thread_id INT, "
				+ "CONSTRAINT thread_id "
				+ "FOREIGN KEY(thread_id)"
				+ "REFERENCES word_threads(id));";
		connection.execute(createQuery);
	}
	 
	 private void createThreadTableNotExists() {
		 String createQuery = "CREATE TABLE IF NOT EXISTS word_threads("
		 		+ "id SERIAL UNIQUE, "
		 		+ "name VARCHAR(50));";
		 connection.execute(createQuery);
	 }
	 
	 private boolean checkThreadPresent(String name) {
		 String checkQuery = "SELECT * FROM word_threads WHERE name=?;";
			return connection.query(checkQuery, threadMapper, name).size()>0;
	 }
	 
	 public int getThreadIdForName(String name) {
		 String getQuery = "SELECT * FROM word_threads WHERE name =?;";
		 List<SingleWordThread> result = connection.query(getQuery, threadMapper, name);
		 if(result.isEmpty()) {
			 return -1;
		 }
		 return result.get(0).getId();
	 }
	 
	/**
	 * this method is used to persist messages, currently only saves it to single table, 
	 * the one-to-many relation with threads will be implemented later
	 * regex checks for characters used, could check using bean validation, but since only one param is passed
	 * from the post it will only check that the message word is right
	 */
	public void addMessage(SingleWordMessage message, String threadName) {
		int threadId = getThreadIdForName(threadName);
		if(threadId<0) throw new IllegalArgumentException("no thread for name "+threadName+" was found");
		if(message == null)throw new IllegalArgumentException("no message passed");
		if(message.getWord()==null ||message.getWord().length()>50)
			throw new IllegalArgumentException("no message content or contents are too big");
		if(message.getRandomName()==null ||message.getRandomName().length()>10
				||message.getRandomName().length()<3) throw new IllegalArgumentException("invalid name");
		if(message.getWord().matches("[A-Za-z0-9\'\"\\`?!\\-*\\(\\)\\[\\]\\{\\}/]{1,50}")) {
			String saveQuery = "INSERT INTO thread_messages (word,sent_at,username,thread_id) VALUES (?,?,?,?)";
			connection.update(saveQuery,message.getWord(),message.getSentAt(),message.getRandomName(), threadId);			
		}else {
			throw new IllegalArgumentException("word contains illegal characters, legal are:"
					+ "\n digits, letters, braces, question mark, quotes, double quotes, minus, and exclamation mark");
		}
	}
	
	/**
	 * used to get all messages, should later make it to retrieve only messages from specific thread
	 */
	public List<SingleWordMessage> getAllMessages(int threadId){
		String getQuery = "SELECT * FROM thread_messages WHERE thread_id=?";
		return connection.query(getQuery,messageMapper,threadId);
	}
	
	
	public void addThread(String threadName) {
		if(!checkThreadPresent(threadName)) {
			String addQuery = "INSERT INTO word_threads (name) VALUES (?);";
			connection.update(addQuery,threadName);			
		}
	}
	
	public List<SingleWordThread> getAllThreads() {
		String getThreadsQuery = "SELECT * FROM word_threads";
		return connection.query(getThreadsQuery, threadMapper);
	}
}
