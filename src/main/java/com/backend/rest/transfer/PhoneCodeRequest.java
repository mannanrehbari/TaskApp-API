package com.backend.rest.transfer;

public class PhoneCodeRequest {
	
	String phoneNumber;
	String smsCode;

	public PhoneCodeRequest() {
	}
	
	public PhoneCodeRequest(String phoneNumber, String smsCode) {
		super();
		this.phoneNumber = phoneNumber;
		this.smsCode = smsCode;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public String toString() {
		return "PhoneCodeRequest [phoneNumber=" + phoneNumber + ", smsCode=" + smsCode + "]";
	}
	
	
}
