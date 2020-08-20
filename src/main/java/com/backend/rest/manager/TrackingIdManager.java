package com.backend.rest.manager;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.repository.ServiceRequestRepository;

@Service
public class TrackingIdManager {
	
	
	@Autowired
	private ServiceRequestRepository serviceRequestRepository;
	
	public String uniqueTrackingId() {
		String trackingId;
		while(true) {
			 trackingId= RandomStringUtils.randomAlphanumeric(6);
			 if(!serviceRequestRepository.existsByTrackingId(trackingId)) {
				 break;
			 }
		}
		return trackingId;
	}

}
