package com.backend.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.entity.ServiceType;
import com.backend.rest.repository.ServiceTypeRepository;

@RestController
@RequestMapping("/api/v1/servicetype")
public class ServiceTypeController {
	
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ServiceType add(@Valid @RequestBody ServiceType serviceType) {
		
		serviceType.setServiceType(fixCase(serviceType.getServiceType()));
		Optional<ServiceType> serviceTypeOpt = serviceTypeRepository.
				findByServiceType(serviceType.getServiceType());
		if(serviceTypeOpt.isPresent()) {
			return serviceTypeOpt.get();
		} else {
			return serviceTypeRepository.save(serviceType);			
		}
	}
	
	@GetMapping("/all")
	public List<ServiceType> findAll(){
		return serviceTypeRepository.findAll();
	}
	
	private String fixCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();		
	}
	
	

}
