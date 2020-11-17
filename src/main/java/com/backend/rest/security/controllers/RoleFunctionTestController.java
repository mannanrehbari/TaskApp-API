package com.backend.rest.security.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class RoleFunctionTestController {
	
	Logger logger = LoggerFactory.getLogger(RoleFunctionTestController.class);
	
	@Value("${iwork.sms.client.hash}")
	private String smsHash;
	
	@GetMapping("/all")
	public String allAccess() {
		logger.info("hello from logger");
		return "Public Content.";
	}
	
	@GetMapping("/seeker")
	@PreAuthorize("hasRole('SEEKER')")
	public String userAccess() {
		return "Seeker Authorized Content.";
	}
	
	@GetMapping("/keyTest")
	public String keyTest() {
		return smsHash;
	}

	@GetMapping("/tasker")
	@PreAuthorize("hasRole('TASKER')")
	public String moderatorAccess() {
		return "Tasker Authorized Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Authorized Board.";
	}
}
