package com.backend.rest.transfer;

import com.backend.rest.entity.PaymentInformation;
import com.backend.rest.entity.ServiceRequest;

public class RequestComplete {
	
	private ServiceRequest request;
	private PaymentInformation paymentInfo;

	public RequestComplete() {
	}

	public RequestComplete(ServiceRequest request, PaymentInformation paymentInfo) {
		this.request = request;
		this.paymentInfo = paymentInfo;
	}
	
	public ServiceRequest getRequest() {
		return request;
	}
	public void setRequest(ServiceRequest request) {
		this.request = request;
	}
	public PaymentInformation getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInformation paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	
	

}
