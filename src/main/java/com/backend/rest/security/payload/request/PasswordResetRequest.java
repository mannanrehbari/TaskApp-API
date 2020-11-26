package com.backend.rest.security.payload.request;

public class PasswordResetRequest {
	
	private String email;
	private Long code;
	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
