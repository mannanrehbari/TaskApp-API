package com.backend.rest.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailManager {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendVerificationMail(String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		
		msg.setSubject("testing boot mail sender");
		msg.setText("this actually works");
		
		mailSender.send(msg);
		System.out.println("Mail was sent to: " + email);
		
	}

}
