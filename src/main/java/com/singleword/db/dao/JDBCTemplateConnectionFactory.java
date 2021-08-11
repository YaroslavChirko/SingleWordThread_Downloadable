package com.singleword.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateConnectionFactory {
	@Autowired
	private static JdbcTemplate jdbcTemplate;
	
	private static JdbcTemplateConnectionFactory jdbcConnection;
	
	private JdbcTemplateConnectionFactory() {}
	
	//if used separately from Spring without autowire still can set own template
	public JdbcTemplateConnectionFactory(JdbcTemplate jdbcTemplate) {
		if(jdbcTemplate != null)
		JdbcTemplateConnectionFactory.jdbcTemplate = jdbcTemplate;
	}
	
	public static JdbcTemplateConnectionFactory getConnection() {
		if(jdbcTemplate == null) throw new IllegalStateException("Template hasn`t been set yet, use other constructor");
		if(JdbcTemplateConnectionFactory.jdbcConnection == null) {
			JdbcTemplateConnectionFactory.jdbcConnection = new JdbcTemplateConnectionFactory();
		}
		return jdbcConnection;
	}
	
	//used if instance cannot be autowired, also can be used to change the connection provider
	public static JdbcTemplateConnectionFactory getConnection(JdbcTemplate jdbcTemplate) {
		if(JdbcTemplateConnectionFactory.jdbcConnection==null) {
			JdbcTemplateConnectionFactory.jdbcConnection = new JdbcTemplateConnectionFactory(jdbcTemplate);
		}else {
			JdbcTemplateConnectionFactory.jdbcTemplate = jdbcTemplate;
		}
		
		return jdbcConnection;
	}

	public static synchronized JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	
}
