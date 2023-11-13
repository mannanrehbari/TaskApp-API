package com.backend.rest.manager;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.rest.repository.ServiceRequestRepository;

@Service
public class TrackingIdGenerator {
	
	
	@Autowired
	private ServiceRequestRepository serviceRequestRepository;
	
	public Long uniqueTrackingId() {
		Long trackingId;
		while(true) {
			 trackingId= RandomUtils.nextLong(100000L, 999999L);
			 if(!serviceRequestRepository.existsByTrackingId(String.valueOf(trackingId))) {
				 break;
			 }
		}
		return trackingId;
	}

}
