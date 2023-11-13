package com.backend.rest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersprofile")
public class UsersProfile {
	
	@Id
	private Long id;

	private String firstName;
	private String lastName;
	
	private boolean emailVer;
	
	public UsersProfile() {
	}
	
	public UsersProfile(Long id, String firstName, String lastName, boolean emailVer) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailVer = emailVer;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public boolean isEmailVer() {
		return emailVer;
	}

	public void setEmailVer(boolean emailVer) {
		this.emailVer = emailVer;
	}

	@Override
	public String toString() {
		return "UsersProfile [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailVer="
				+ emailVer + "]";
	}
	
	
}
