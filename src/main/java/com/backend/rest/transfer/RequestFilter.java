package com.backend.rest.transfer;

import java.util.Date;

import com.backend.rest.enums.RequestStatus;

public class RequestFilter {

	Integer locationId;
	Integer serviceTypeId;
	RequestStatus requestStatus;

	// strings for clarity
	String serviceType;

	Date minDate;
	Date maxDate;
	
	public RequestFilter() {
		super();
	}

	public RequestFilter(Integer locationId, Integer serviceTypeId, RequestStatus requestStatus, String serviceType,
			Date minDate, Date maxDate) {
		super();
		this.locationId = locationId;
		this.serviceTypeId = serviceTypeId;
		this.requestStatus = requestStatus;
		this.serviceType = serviceType;
		this.minDate = minDate;
		this.maxDate = maxDate;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	@Override
	public String toString() {
		return "RequestFilter [locationId=" + locationId + ", serviceTypeId=" + serviceTypeId + ", requestStatus="
				+ requestStatus + ", serviceType=" + serviceType + ", minDate=" + minDate
				+ ", maxDate=" + maxDate + "]";
	}
	

}
