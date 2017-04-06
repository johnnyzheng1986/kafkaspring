package com.unionpay.datacenter.kafka.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@ComponentScan("com.unionpay")
public class SpringConfiguration {
	
	private static  Logger logger = LoggerFactory.getLogger(SpringConfiguration.class);
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
        InputStream in = SpringConfiguration.class.getResourceAsStream("/jdbc.properties");  
        Properties properties = new Properties();  
        try {
			properties.load(in);
		} catch (IOException e) {
			logger.error("jdbc配置文件加载错误"+e.getMessage());
		}  
		//MySQL database we are using
		dataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(properties.getProperty("jdbc.url"));
		dataSource.setUsername(properties.getProperty("jdbc.username"));
		dataSource.setPassword(properties.getProperty("jdbc.password"));
		
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}
	
//	@Bean
//	public DayBatchDao dayBatchDao(){
//		DayBatchDaoImpl dayBatchDaoImpl = new DayBatchDaoImpl();
//		dayBatchDaoImpl.setJdbcTemplate(jdbcTemplate());
//		return dayBatchDaoImpl;
//	}
//	@Bean
//	public LegoIndBasFiles2DbDao legoIndBasFiles2DbDao(){
//		LegoIndBasFiles2DbDaoImpl legoIndBasFiles2DbDaoImpl = new LegoIndBasFiles2DbDaoImpl();
//		legoIndBasFiles2DbDaoImpl.setJdbcTemplate(jdbcTemplate());
//		return legoIndBasFiles2DbDaoImpl;
//	}
}
