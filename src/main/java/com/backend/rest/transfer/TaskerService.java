package com.backend.rest.transfer;

public class TaskerService {
	
	private Long taskerId;
	private Long serviceId;
	
	public Long getTaskerId() {
		return taskerId;
	}
	public void setTaskerId(Long taskerId) {
		this.taskerId = taskerId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
	@Override
	public String toString() {
		return "TaskerService [taskerId=" + taskerId + ", serviceId=" + serviceId + "]";
	}
	

}
