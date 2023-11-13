package com.backend.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.entity.PaymentInformation;
import com.backend.rest.manager.PaymentInformationManager;

@RestController
@RequestMapping("/api/v1/paymentinfo")
public class PaymentInfoController {
	
	@Autowired
	private PaymentInformationManager paymentInfoManager;
	
	@GetMapping("/{trackingId}")
	public PaymentInformation getPaymentInfo(@PathVariable("trackingId") String trackingId) {
		return paymentInfoManager.findByReqTrackingId(trackingId);
	}

}
