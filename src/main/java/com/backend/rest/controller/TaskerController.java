package com.backend.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.rest.payload.TaskerRequest;
import com.backend.rest.security.enums.ERole;
import com.backend.rest.security.models.Role;
import com.backend.rest.security.models.User;
import com.backend.rest.security.payload.request.SignupRequest;
import com.backend.rest.security.payload.response.MessageResponse;
import com.backend.rest.security.repository.RoleRepository;
import com.backend.rest.security.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/tasker")
public class TaskerController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TaskerRequest> findAllTaskers(){
		List<Long> taskerIds = userRepository.findAllTaskerIds();
		List<User> taskers = userRepository.findAllById(taskerIds);
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_TASKER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		
		List<TaskerRequest> taskerss = new ArrayList<>();
		
		List<Object[]> taskerServices = userRepository.findTaskerServiceMapping();
		Map<Long, Long> taskerServiceMap = new HashMap<>();
		
		for(Object[] tskrSrvc: taskerServices) {
			taskerServiceMap.put(
					Long.valueOf(tskrSrvc[0].toString()), 
					Long.valueOf(tskrSrvc[1].toString())
					);
		}
		
		for(User tasker: taskers) {
			TaskerRequest taskerr = new TaskerRequest();
			taskerr.setId(tasker.getId());
			taskerr.setEmail(tasker.getEmail());
			taskerr.setPassword(null);
			taskerr.setRoles(roles);
			Long serviceTypeId = taskerServiceMap.get(tasker.getId());
			taskerr.setServiceTypeId(serviceTypeId);
			taskerss.add(taskerr);
		}
		
		return taskerss;
	}
	
	
	@GetMapping("/type/{serviceTypeId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<TaskerRequest> getTaskersByType(@PathVariable("serviceTypeId") Long serviceId){
		List<Object []> serviceTaskers = userRepository.findTaskerServiceMappingByServiceType(serviceId);
		List<Long> serviceTaskerIds = new ArrayList<>();
		serviceTaskerIds = serviceTaskers.stream().map(
				(serviceTasker)->{ return Long.valueOf(serviceTasker[0].toString());
				}).collect(Collectors.toList());
		;
		
		List<User> foundTaskers = userRepository.findAllById(serviceTaskerIds);
		List<TaskerRequest> taskers = new ArrayList<>();
		for(User tasker: foundTaskers) {
			TaskerRequest taskerr = new TaskerRequest();
			taskerr.setEmail(tasker.getEmail());
			taskerr.setId(tasker.getId());
			taskerr.setServiceTypeId(serviceId);
			taskers.add(taskerr);
		}
		
		return taskers;
	}
	
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTasker(@Valid @RequestBody TaskerRequest taskerSignUpRequest) {
		
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setEmail(taskerSignUpRequest.getEmail());
		signUpRequest.setPassword(taskerSignUpRequest.getPassword());
		signUpRequest.setUsername(taskerSignUpRequest.getEmail());

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getEmail(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_TASKER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		user.setRoles(roles);
		User savedUser = userRepository.save(user);
		
		// tasker is saved
		// map service type to tasker
		userRepository.insertTaskerServiceType(savedUser.getId(), taskerSignUpRequest.getServiceTypeId());
		

		return ResponseEntity.ok(new MessageResponse("Tasker registered successfully!"));
	}
	
}
