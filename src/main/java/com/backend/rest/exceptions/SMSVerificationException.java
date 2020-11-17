package com.backend.rest.exceptions;

public class SMSVerificationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public SMSVerificationException() {}
	public SMSVerificationException(String exception){
		super(exception);
	}
}
