package com.backend.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.mail.req")
	public JavaMailSender reqEmailSender() {
		return new JavaMailSenderImpl();
	}
	
	@Bean
	@ConfigurationProperties("spring.mail.auth")
	public JavaMailSender authEmailSender() {
		return new JavaMailSenderImpl();
	}
	
}
