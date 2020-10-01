package com.backend.rest.transfer;

import com.backend.rest.enums.RequestStatus;

public class RequestSearchCriteria {
	
	private Integer locationId;
	private Integer serviceTypeId;
	
	private RequestStatus requestStatus;

	public RequestSearchCriteria() {
	}

	public RequestSearchCriteria(Integer locationId, Integer serviceTypeId, RequestStatus requestStatus) {
		this.locationId = locationId;
		this.serviceTypeId = serviceTypeId;
		this.requestStatus = requestStatus;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	@Override
	public String toString() {
		return "RequestSearchCriteria [locationId=" + locationId + ", serviceTypeId=" + serviceTypeId
				+ ", requestStatus=" + requestStatus + "]";
	}


}
