package com.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.exceptions.SMSVerificationException;
import com.backend.rest.manager.SMSCodeManager;
import com.backend.rest.transfer.PhoneCodeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/v1/sms")
public class SmsController {
	
	@Autowired
	private SMSCodeManager smsManager;
	
	@GetMapping("/sendsms/{phoneNumber}")
	public PhoneCodeRequest testEV(@PathVariable("phoneNumber") String phoneNumber) throws JsonMappingException, JsonProcessingException {
		return smsManager.createVerCode(phoneNumber);
	}
	
	@GetMapping("/verify/{phoneNumber}/{trackingId}")
	public PhoneCodeRequest verifyCode(
			@PathVariable("phoneNumber") String phoneNumber,
			@PathVariable("trackingId") String trackingId			
			) throws SMSVerificationException {
		return smsManager.verifyPhoneCode(phoneNumber, trackingId);
	}
	
	@GetMapping("/checkquota")
	public String checkQuota() {
		return smsManager.checkQuota();
	}

}
