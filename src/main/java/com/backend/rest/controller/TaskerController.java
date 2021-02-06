package com.backend.rest.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.rest.entity.TaskerDetails;
import com.backend.rest.manager.TaskerDetailsManager;
import com.backend.rest.payload.TaskerRequest;
import com.backend.rest.repository.TaskerDetailsRepository;
import com.backend.rest.security.enums.ERole;
import com.backend.rest.security.models.Role;
import com.backend.rest.security.models.User;
import com.backend.rest.security.payload.request.SignupRequest;
import com.backend.rest.security.payload.response.MessageResponse;
import com.backend.rest.security.repository.RoleRepository;
import com.backend.rest.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/tasker")
public class TaskerController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskerDetailsRepository taskerDetailsRepository;
	
	@Autowired
	private TaskerDetailsManager detailsManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/all-taskers")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TaskerDetails> allTaskers() {
		List<TaskerDetails> allTaskers = taskerDetailsRepository.findAll();
		return allTaskers;
	}

	@GetMapping("/type/{serviceTypeId}")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TaskerDetails> taskersByType(@PathVariable("serviceTypeId") Long serviceId) {
		return detailsManager.getTaskersWithoutImage(serviceId);
	}

	@RequestMapping(value = "/add-new", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<?> addNewTasker(
			@RequestPart("taskerRequest") String taskerRequest,
			@RequestPart("taskerDetails") String taskerDetails, 
			@RequestPart("cnicImg") MultipartFile file)
			throws IOException {
		TaskerRequest taskerReq = objectMapper.readValue(taskerRequest, TaskerRequest.class);
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setEmail(taskerReq.getEmail());
		signUpRequest.setPassword(taskerReq.getPassword());
		signUpRequest.setUsername(taskerReq.getEmail());

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

		TaskerDetails newTaskerDetails = objectMapper.readValue(taskerDetails, TaskerDetails.class);
		newTaskerDetails.setUserId(savedUser.getId());
		newTaskerDetails.setImgType(file.getContentType());
		newTaskerDetails.setCnicImg(file.getBytes());
		TaskerDetails fromDb = taskerDetailsRepository.save(newTaskerDetails);
		return ResponseEntity.ok(new MessageResponse("Tasker registered!"));
	}

}
