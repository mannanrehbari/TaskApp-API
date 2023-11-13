package com.backend.rest.security.payload.response;

import com.backend.rest.security.enums.PasswordResetStatus;

public class PasswordResetResponse {
	
	private String email;
	private String message;
	private PasswordResetStatus status;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PasswordResetStatus getStatus() {
		return status;
	}
	public void setStatus(PasswordResetStatus status) {
		this.status = status;
	}
	
	

}
