package com.backend.rest.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.entity.PaymentInformation;
import com.backend.rest.repository.PaymentInformationRepository;

@Service
public class PaymentInformationManager {
	
	@Autowired
	private PaymentInformationRepository paymentInformationRepository;
	
	public PaymentInformation savePaymentInfo(PaymentInformation paymentInformation) {
		return paymentInformationRepository.save(paymentInformation);
	}
	
	public PaymentInformation findByReqTrackingId(String reqTrackingId) {
		return paymentInformationRepository.findByReqTrackingId(reqTrackingId).get();
	}

}
