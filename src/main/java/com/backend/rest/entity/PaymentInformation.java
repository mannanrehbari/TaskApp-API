package com.backend.rest.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.backend.rest.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="paymentinformation")
public class PaymentInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long taskerId;
	private Long seekerId;
	
	private String reqTrackingId;
	
	private Long paymentAmount;
	
	private PaymentMethod paymentMethod;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime paymentDate;
	
	public PaymentInformation() {
	}

	public PaymentInformation(Long taskerId, Long seekerId, String reqTrackingId, Long paymentAmount,
			PaymentMethod paymentMethod, LocalDateTime paymentDate) {
		this.taskerId = taskerId;
		this.seekerId = seekerId;
		this.reqTrackingId = reqTrackingId;
		this.paymentAmount = paymentAmount;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskerId() {
		return taskerId;
	}

	public void setTaskerId(Long taskerId) {
		this.taskerId = taskerId;
	}

	public Long getSeekerId() {
		return seekerId;
	}

	public void setSeekerId(Long seekerId) {
		this.seekerId = seekerId;
	}

	public String getReqTrackingId() {
		return reqTrackingId;
	}

	public void setReqTrackingId(String reqTrackingId) {
		this.reqTrackingId = reqTrackingId;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}	

}
