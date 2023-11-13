package com.backend.rest.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.backend.rest.enums.EmailStatus;
import com.backend.rest.security.payload.request.PasswordResetRequest;
import com.backend.rest.transfer.EmailSendResults;

import net.jodah.expiringmap.ExpiringMap;

@Service
public class EmailManager {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private static Long emailCodeLife = 10L;
	private Map<String, Long> emailCodeMap = ExpiringMap.builder().expiration(emailCodeLife, TimeUnit.MINUTES).build();
	
	@Autowired
	private JavaMailSender reqEmailSender;
	
	@Autowired
	private JavaMailSender authEmailSender;
	
	
	public EmailSendResults sendSimpleEmail() {
		EmailSendResults emailRes = new EmailSendResults();
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("test1@iwork.pk");
			msg.setTo("aqibjaveed508@gmail.com");
			msg.setSubject("awesome ");
			msg.setText("This is an official email");
			mailSender.send(msg);
			emailRes.setMessage("Email send successful");
			emailRes.setStatus(EmailStatus.SUCCESSFUL);
			return emailRes;
		} catch(MailException e) {
			e.printStackTrace();
			emailRes.setMessage("Error occurred sending email");
			emailRes.setStatus(EmailStatus.FAILED);
			return emailRes;
		}
	}
	
	// send emailVerificationCode
	public EmailSendResults sendEmailVerCode(String email) {
		EmailSendResults results = new EmailSendResults();
		SimpleMailMessage message = new SimpleMailMessage();
		if(emailCodeMap.containsKey(email)) {
			results.setMessage("You have recently requested a code, try again after 10minutes");
			results.setStatus(EmailStatus.FAILED);
		} else {
			// get code
			// send code
			Long emailCode = generateEmailCode(email);
			emailCodeMap.put(email, emailCode);
			message.setFrom("auth-notification-dnr@iwork.pk");
			message.setSubject("iWork password Reset");
			message.setText("Your verification code is " + emailCode);
			message.setTo(email);
			authEmailSender.send(message);
			results.setMessage("Code sent. Valid for 10 minutes");
			results.setStatus(EmailStatus.SUCCESSFUL);
		}
		return results;
	}
	
	public boolean verifyEmailCode(PasswordResetRequest request) {
		return request.getCode() == emailCodeMap.get(request.getEmail()) ? true : false;
	}
	
	
	
	
	// signup successful
	// forgot password email link
	
	// request created
	// tasker assigned
	// task completed
	
	
	//emailCodeGenerator 
	private Long generateEmailCode(String email) {
		Long emailCode;
		while(true) {
			if(this.emailCodeMap.containsKey(email)) {
				continue;
			} else {
				emailCode = RandomUtils.nextLong(1000L, 9999L);
				break;
			}
		}
		return emailCode;
	}
	

}
