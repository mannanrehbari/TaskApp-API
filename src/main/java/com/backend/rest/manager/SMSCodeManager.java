package com.backend.rest.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.rest.enums.SMSCodeStatus;
import com.backend.rest.exceptions.SMSVerificationException;
import com.backend.rest.transfer.PhoneCodeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.jodah.expiringmap.ExpiringMap;

@Service
public class SMSCodeManager {

	@Autowired
	private TrackingIdGenerator trackingIdManager;

	@Value("${iwork.sms.client.hash}")
	private String smsHash;
	
	@Value("${iwork.sms.client.url}")
	private String smsApiUrl;
	
	@Value("${iwork.sms.client.quota}")
	private String smsQuota;
	
	@Value("${iwork.sms.client.maskingId}")
	private String smsMaskId;
	
	private static Long smsCodeLife = 5L;
	
	private RestTemplate restTemplate = new RestTemplate();
	private Map<String, String> phoneCodeMap = ExpiringMap.builder().expiration(smsCodeLife, TimeUnit.MINUTES).build();

	private String sendSMS(String phoneNumber, String trackingId) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36");
		
		String smsBody = "Your tracking ID is: " + trackingId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(smsApiUrl)
				.queryParam("hash", smsHash)
				.queryParam("receivernum", phoneNumber)
				.queryParam("sendernum", smsMaskId)
				.queryParam("textmessage", smsBody);			

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, entity,
				String.class);
		String responseBodyJson = response.getBody();
		return responseBodyJson;
	}	
	
	// send trackingId to phoneNumber
	public PhoneCodeRequest createVerCode(String phoneNumber) {
		PhoneCodeRequest pcReq = new PhoneCodeRequest();
		pcReq.setPhoneNumber(phoneNumber);
		try {
			if(phoneCodeMap.containsKey(phoneNumber)) {
				pcReq.setMessage("Try again after " + smsCodeLife + " minutes");
				pcReq.setStatus(SMSCodeStatus.LIMIT_EXCEEDED);
				return pcReq;
			}
			String newTrackingId = this.trackingIdManager.uniqueTrackingId();
			phoneCodeMap.put(phoneNumber, newTrackingId);
			String clientResp = sendSMS(phoneNumber, newTrackingId);
			pcReq.setMessage(clientResp);
			pcReq.setStatus(SMSCodeStatus.SUCCESSFUL);
			return pcReq;
		} catch (Exception e) {
			e.getMessage();
		}
		pcReq.setStatus(SMSCodeStatus.FAILED);
		return pcReq;
	}
	
	//verify trackingId for phoneNumber
	public PhoneCodeRequest verifyPhoneCode(String phoneNumber, String trackingId) throws SMSVerificationException {
		String inMapCode = phoneCodeMap.get(phoneNumber);
		PhoneCodeRequest pcReq = new PhoneCodeRequest();
		pcReq.setPhoneNumber(phoneNumber);
		if (StringUtils.isNotEmpty(inMapCode) && StringUtils.equals(inMapCode, trackingId)) {
			pcReq.setMessage("Input matches the sms code");
			pcReq.setStatus(SMSCodeStatus.SUCCESSFUL);
			return pcReq;
		} else {
			pcReq.setMessage("The input does not match sms code");
			pcReq.setStatus(SMSCodeStatus.INCORRECT);
			return pcReq;
		}
	}
	
	//check
	public String checkQuota() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(smsQuota)
				.queryParam("hash", smsHash);
							
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, entity,
				String.class);
		String responseBodyJson = response.getBody();
		return responseBodyJson;
	}

}
