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

import com.backend.rest.entity.Location;
import com.backend.rest.repository.LocationRepository;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
	
	@Autowired
	private LocationRepository locationRepository;
	
		
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public Location saveLocation(@Valid @RequestBody Location location) {
		location.setCity(fixCase(location.getCity()));
		location.setProvince(fixCase(location.getProvince()));
		location.setCountry(fixCase(location.getCountry()));
		
		Optional<Location> locOpt = locationRepository.
				findByCityAndProvinceAndCountry(
						location.getCity(), 
						location.getProvince(), 
						location.getCountry());
		if(locOpt.isPresent()) {
			return locOpt.get();
		}
		return locationRepository.save(location);
	}
	
	@GetMapping("/all")
	public List<Location> getLocations(){
		return locationRepository.findAll();		
	}
	
	
	private String fixCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();		
	}
	
	

}
