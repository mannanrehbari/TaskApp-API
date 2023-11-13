package com.backend.rest.security.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.security.payload.request.PasswordResetRequest;
import com.backend.rest.security.payload.response.PasswordResetResponse;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordResetController {
	
	
	
	
	
	@PostMapping("/send-resetcode")
	public PasswordResetResponse resetPassword(@RequestBody PasswordResetRequest request) {
		PasswordResetResponse response = new PasswordResetResponse();
		
		// send code to email provided
		
		// save code in map
		
		return response;
	}
	
	@PostMapping("/verify-code")
	public PasswordResetResponse verifyCode(@RequestBody PasswordResetRequest request) {
		PasswordResetResponse response = new PasswordResetResponse();
		// check if in map
		
		
		return response;
	}
	
}
