package com.backend.rest.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.backend.rest.converters.RequestStatusJpaConverter;
import com.backend.rest.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "servicerequests")
public class ServiceRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// assigned by system on valid input
	private String trackingId;
	
	// assigned by users later
	private Long assignedTaskerId;
	private String seekerEmail;
	private String reqLat;
	private String reqLng;
	
	@NotNull
	private String firstName; //user input
	@NotNull
	private String lastName; // user input
	@NotNull
	private String address; //user input
	
	@NotNull
	private String details; //user input

	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date requestDate; //user drop down
	
	@NotNull
	private Integer serviceTypeId;//user drop down
	@NotNull
	private Integer locationId; //user drop down
	
	
	// when above are entered	
	@NotNull
	private String seekerPhone; //user input on pop up
	
	@NotNull
	@Convert(converter = RequestStatusJpaConverter.class)
	private RequestStatus requestStatus; //system

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDateTime; //system
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime assignedTime;

	public ServiceRequest() {
	}

	public ServiceRequest(@NotNull String firstName, @NotNull String lastName,
			@NotNull String seekerPhone, @NotNull String address, @NotNull String details,
			@NotNull Integer serviceTypeId, @NotNull Integer locationId, @NotNull RequestStatus requestStatus,
			@NotNull Date requestDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.seekerPhone = seekerPhone;
		this.address = address;
		this.details = details;
		this.serviceTypeId = serviceTypeId;
		this.locationId = locationId;
		this.requestStatus = requestStatus;
		this.requestDate = requestDate;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSeekerPhone() {
		return seekerPhone;
	}

	public void setSeekerPhone(String seekerPhone) {
		this.seekerPhone = seekerPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Long getAssignedTaskerId() {
		return assignedTaskerId;
	}

	public void setAssignedTaskerId(Long assignedTaskerId) {
		this.assignedTaskerId = assignedTaskerId;
	}

	public String getSeekerEmail() {
		return seekerEmail;
	}

	public void setSeekerEmail(String seekerEmail) {
		this.seekerEmail = seekerEmail;
	}

	public String getReqLat() {
		return reqLat;
	}

	public void setReqLat(String reqLat) {
		this.reqLat = reqLat;
	}

	public String getReqLng() {
		return reqLng;
	}

	public void setReqLng(String reqLng) {
		this.reqLng = reqLng;
	}
	
	public LocalDateTime getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(LocalDateTime assignedTime) {
		this.assignedTime = assignedTime;
	}

	@Override
	public String toString() {
		return "ServiceRequest [id=" + id + ", trackingId=" + trackingId + ", assignedTaskerId=" + assignedTaskerId
				+ ", seekerEmail=" + seekerEmail + ", reqLat=" + reqLat + ", reqLng=" + reqLng + ", firstName="
				+ firstName + ", lastName=" + lastName + ", address=" + address + ", details=" + details
				+ ", requestDate=" + requestDate + ", serviceTypeId=" + serviceTypeId + ", locationId=" + locationId
				+ ", seekerPhone=" + seekerPhone + ", requestStatus=" + requestStatus + ", createdDateTime="
				+ createdDateTime + "]";
	}
	
	

}
