package com.backend.rest.transfer;

import com.backend.rest.enums.EmailStatus;

public class EmailSendResults {
	
	private String message;
	private EmailStatus status;

	public EmailSendResults() {
	}
	
	public EmailSendResults(String message, EmailStatus status) {
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public EmailStatus getStatus() {
		return status;
	}
	public void setStatus(EmailStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "EmailSendResults [message=" + message + ", status=" + status + "]";
	}
	
	

}
