package com.backend.rest.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.manager.SendMailManager;
import com.backend.rest.security.payload.request.EmailRequest;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordResetController {
	
	
	@Autowired
	private SendMailManager sendMailManager;
	
	
	@PostMapping(value = "/sendcode")
	public EmailRequest sendVerificationCode(@RequestBody EmailRequest email) {
		// send code to email.getEmail()
		sendMailManager.sendVerificationMail(email.getEmail());;
		return email;
	}

}
