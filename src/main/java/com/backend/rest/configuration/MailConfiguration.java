package com.backend.rest.configuration;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
//	@Bean
//	@ConfigurationProperties(prefix = "spring.mail.request")
//	public JavaMailSender requestMailSender() {
//		return new JavaMailSenderImpl();
//	}	
//	
//	@Bean
//	@ConfigurationProperties(prefix = "spring.mail")
//	public JavaMailSender authMailSender() {
//		return new JavaMailSenderImpl();
//	}
	@Bean
	@ConfigurationProperties(prefix = "spring.mail.auth")
	public Properties authMailProps() {
		return new Properties();
	}
	
}
