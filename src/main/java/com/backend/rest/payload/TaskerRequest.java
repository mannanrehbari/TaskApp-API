package com.backend.rest.payload;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.backend.rest.security.models.Role;

public class TaskerRequest {
	
	private Long id;
	 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<Role> roles;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotNull
    private Long serviceTypeId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Long serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
    
    
    

}
