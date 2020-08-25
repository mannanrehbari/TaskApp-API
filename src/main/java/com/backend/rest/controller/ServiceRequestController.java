package com.backend.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.entity.ServiceRequest;
import com.backend.rest.enums.RequestStatus;
import com.backend.rest.manager.TrackingIdManager;
import com.backend.rest.repository.ServiceRequestRepository;

@RestController
@RequestMapping("api/v1/request")
public class ServiceRequestController {
	
	
	@Autowired
	private ServiceRequestRepository serviceReqRepository;
	
	@Autowired
	private TrackingIdManager trackingIdManager;
	
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@PostMapping("/add")
	public ServiceRequest addRequest(@Valid @RequestBody ServiceRequest serviceRequest) {
		String trackingId = trackingIdManager.uniqueTrackingId();
		
		serviceRequest.setTrackingId(trackingId);
		serviceRequest.setCreatedDateTime(new Date());
		serviceRequest.setRequestStatus(RequestStatus.STARTED);
		
		System.out.println(serviceRequest.toString());
		return serviceReqRepository.save(serviceRequest);
	}
	
	@GetMapping("/track/{trackingId}")
	public ServiceRequest trackRequest(@PathVariable("trackingId") String trackingId) {
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findByTrackingId(trackingId);
		if(reqOpt.isPresent()) {
			return reqOpt.get();
		}
		return null;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<ServiceRequest> allRequests(){
		return serviceReqRepository.findAll();		
	}
	
}
