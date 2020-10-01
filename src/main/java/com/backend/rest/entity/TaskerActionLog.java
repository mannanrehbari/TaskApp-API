package com.backend.rest.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.backend.rest.enums.RequestStatus;

@Entity
@Table(name = "taskeractionlogs")
public class TaskerActionLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long taskerId;
	private String requestTrackingId;
	private RequestStatus statusAction;
	private Date logDate;

	public TaskerActionLog() {
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
	public String getRequestTrackingId() {
		return requestTrackingId;
	}
	public void setRequestTrackingId(String requestTrackingId) {
		this.requestTrackingId = requestTrackingId;
	}
	public RequestStatus getStatusAction() {
		return statusAction;
	}
	public void setStatusAction(RequestStatus statusAction) {
		this.statusAction = statusAction;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

}
