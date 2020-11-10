package com.backend.rest.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jodah.expiringmap.ExpiringMap;

@Service
public class SMSCodeManager {
	
	// receive a phone number
	// generate unique code
	// save phoneNumber -> code in time expiring map 
	
	@Autowired
	private TrackingIdManager trackingIdManager;
	
	private Map<String, String> phoneCodeMap = ExpiringMap.builder()
			.expiration(1, TimeUnit.MINUTES).build();
	
	public boolean createVerCode(String phoneNumber) {
		try {
			System.out.println(phoneNumber);
			String newTrackingId = this.trackingIdManager.uniqueTrackingId();
			System.out.println(newTrackingId);
			phoneCodeMap.put("phoneNumber", newTrackingId);
			sendToPhone(phoneNumber, newTrackingId);
			return true;
		} catch(Exception e) {
			e.getMessage();
		}
		return false;
	}
	
	
	public boolean sendToPhone(String phoneNumber, String trackingId) {
		try {
			// call third-party
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private boolean verifyPhoneCode(String phoneNumber, String trackingId) {
		String inMapCode = phoneCodeMap.get(phoneNumber);
		if(StringUtils.equals(inMapCode, trackingId)) {
			return true;			
		} else {
			return false;
		}
	}
	
	
	
	
	

}
