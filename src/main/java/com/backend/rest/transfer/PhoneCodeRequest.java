package com.backend.rest.transfer;

import com.backend.rest.enums.SMSCodeStatus;

public class PhoneCodeRequest {
	
	private String phoneNumber;
	private String smsCode;
	private String message;
	
	private SMSCodeStatus status;

	public PhoneCodeRequest() {
	}
	
	public PhoneCodeRequest(String phoneNumber, String smsCode, SMSCodeStatus status) {
		super();
		this.phoneNumber = phoneNumber;
		this.smsCode = smsCode;
		this.message = message;
		this.status = status;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SMSCodeStatus getStatus() {
		return status;
	}

	public void setStatus(SMSCodeStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PhoneCodeRequest [phoneNumber=" + phoneNumber + ", smsCode=" + smsCode + ", message=" + message
				+ ", status=" + status + "]";
	}
	
}
