package com.backend.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskerdetails")
public class TaskerDetails {

	@Id
	private Long userId;

	private Long serviceTypeId;

	private String firstName;
	private String lastName;

	private String phoneNo;
	private String email;
	private String cnicNo;

	// image properties
	private String imgName;
	private String imgType;
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] cnicImg;
	
	public TaskerDetails() {
	}

	public TaskerDetails(Long userId, Long serviceTypeId, String firstName, String lastName, String phoneNo, String email, String cnicNo,
			String imgName, String imgType, byte[] cnicImg) {

		this.userId = userId;
		this.serviceTypeId = serviceTypeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.cnicNo = cnicNo;
		this.imgName = imgName;
		this.imgType = imgType;
		this.cnicImg = cnicImg;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnicNo() {
		return cnicNo;
	}

	public void setCnicNo(String cnicNo) {
		this.cnicNo = cnicNo;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public byte[] getCnicImg() {
		return cnicImg;
	}

	public void setCnicImg(byte[] cnicImg) {
		this.cnicImg = cnicImg;
	}
	

}
