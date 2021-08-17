package com.singleword.db.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@PropertySource("classpath:database.properties")
@Configuration
public class JdbcTemplateConfiguration {

	@Value("${db.url}")
	private String url;
	
	@Value("${db.driverClass}")
	private String driverClass;
	
	@Value("${db.username}")
	private String username;
	
	@Value("${db.password}")
	private String password;
	
	
	@Bean
	public DataSource psqlDataSource(){	
		
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);	
		return dataSource;
	}
	
}
