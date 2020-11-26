package com.backend.rest.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.rest.entity.PaymentInformation;
import com.backend.rest.entity.ServiceRequest;
import com.backend.rest.entity.TaskerActionLog;
import com.backend.rest.enums.RequestStatus;
import com.backend.rest.manager.PaymentInformationManager;
import com.backend.rest.manager.SMSCodeManager;
import com.backend.rest.manager.ServiceRequestManager;
import com.backend.rest.manager.TrackingIdGenerator;
import com.backend.rest.repository.ServiceRequestRepository;
import com.backend.rest.repository.TaskerActionLogRepository;
import com.backend.rest.transfer.PhoneCodeRequest;
import com.backend.rest.transfer.RequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.jodah.expiringmap.ExpiringMap;

@RestController
@RequestMapping("api/v1/request")
public class ServiceRequestController {

	@Autowired
	private ServiceRequestRepository serviceReqRepository;
	
	@Autowired
	private PaymentInformationManager paymentInformationManager;
	
	@Autowired
	private ServiceRequestManager srvcReqManager;
	
	ObjectMapper objectMapper = new ObjectMapper();

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private Map<ServiceRequest, LocalDateTime> requestExpiringMap = ExpiringMap.builder()
			.expiration(15, TimeUnit.MINUTES).asyncExpirationListener((reqExp, val) -> {
				this.expireRequests((ServiceRequest) reqExp);
			}).build();

	@PostMapping("/add")
	public ServiceRequest addRequest(@Valid @RequestBody ServiceRequest serviceRequest) {
		serviceRequest.setCreatedDateTime(LocalDateTime.now());
		serviceRequest.setRequestStatus(RequestStatus.STARTED);
		System.out.println(serviceRequest.toString());
		return serviceReqRepository.save(serviceRequest);
	}
	
	@PostMapping("/edit")
	public ServiceRequest editRequest(@Valid @RequestBody ServiceRequest serviceRequest) {
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findByTrackingId(serviceRequest.getTrackingId());
		if(reqOpt.isPresent()) {
			return serviceReqRepository.save(serviceRequest);
		}
		return new ServiceRequest();
	}

	@GetMapping("/track/{trackingId}")
	public ServiceRequest trackRequest(@PathVariable("trackingId") String trackingId) {
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findByTrackingId(trackingId);
		if (reqOpt.isPresent()) {
			return reqOpt.get();
		}
		return null;
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<ServiceRequest> allRequests() {
		return serviceReqRepository.findAll();
	}
	
	//replicating below
	@PostMapping("/search-criteria")
	public List<ServiceRequest> requestsByCriteria(@RequestBody RequestFilter criteria){
		return srvcReqManager.requestsByCriteria(criteria);
	}
	
	@GetMapping("/{email}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TASKER') or hasRole('SEEKER')")
	public List<ServiceRequest> requestsByEmail(@PathVariable("email") String email){
		return serviceReqRepository.findBySeekerEmail(email);		
	}

	@PostMapping("/assign")
	@PreAuthorize("hasRole('ADMIN')")
	public ServiceRequest assignTasker(@Valid @RequestBody ServiceRequest serviceRequest) {
		requestExpiringMap.remove(serviceRequest);
		Optional<ServiceRequest> opt = serviceReqRepository.findById(serviceRequest.getId());
		if (opt.isPresent()) {
			ServiceRequest reqFromDb = opt.get();
			reqFromDb.setAssignedTaskerId(serviceRequest.getAssignedTaskerId());
			try {
				this.requestExpiringMap.put(reqFromDb, LocalDateTime.now());
				reqFromDb.setRequestStatus(RequestStatus.ASSIGNED);
				reqFromDb.setAssignedTime(LocalDateTime.now());
				return serviceReqRepository.save(reqFromDb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ServiceRequest();
	}

	@PostMapping("/unassign")
	@PreAuthorize("hasRole('ADMIN')")
	public ServiceRequest unassignTasker(@Valid @RequestBody ServiceRequest serviceRequest) {
		requestExpiringMap.remove(serviceRequest);
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findById(serviceRequest.getId());
		if (reqOpt.isPresent()) {
			ServiceRequest req = reqOpt.get();
			req.setAssignedTaskerId(null);
			req.setAssignedTime(null);
			req.setRequestStatus(RequestStatus.STARTED);
			return serviceReqRepository.save(req);
		}
		return new ServiceRequest();
	}

	@GetMapping("/tasker/{taskerId}")
	public List<ServiceRequest> getAllByTasker(@PathVariable("taskerId") Long taskerId) {
		return serviceReqRepository.findByAssignedTaskerId(taskerId);
	}

	@PostMapping("/accept")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TASKER')")
	public ServiceRequest acceptTaskerRequest(@RequestBody ServiceRequest serviceRequest) {
		System.out.println("Map size on accept: " + this.requestExpiringMap.size());
		
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findById(serviceRequest.getId());
		if (reqOpt.isPresent()) {
			ServiceRequest req = reqOpt.get();
			req.setRequestStatus(RequestStatus.IN_PROGRESS);
			return serviceReqRepository.save(req);
		}
		return null;
	}

	@PostMapping("/cancel")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TASKER')")
	public ServiceRequest cancelTaskerRequest(@RequestBody ServiceRequest serviceRequest) {
		System.out.println("Map size on cancel: " + this.requestExpiringMap.size());
		Optional<ServiceRequest> reqOpt = serviceReqRepository.findById(serviceRequest.getId());
		if (reqOpt.isPresent()) {
			ServiceRequest req = reqOpt.get();
			req.setRequestStatus(RequestStatus.CANCELLED);
			req.setAssignedTaskerId(null);
			req.setAssignedTime(null);
			return serviceReqRepository.save(req);

		}
		return null;
	}
	
	@RequestMapping(value = "/complete-payment", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@PreAuthorize("hasRole('ADMIN') or hasRole('TASKER')")
	@ResponseBody
	public PaymentInformation completeRequestPayment(
			@RequestPart("paymentInformation") String paymentInformation,
			@RequestPart("requestReceipt") MultipartFile file) throws Exception {
		try {
			PaymentInformation paymentInfo = objectMapper.readValue(paymentInformation, PaymentInformation.class);
			Optional<ServiceRequest> reqOpt = serviceReqRepository.findByTrackingId(paymentInfo.getReqTrackingId());
			if(reqOpt.isPresent()) {
				ServiceRequest req = reqOpt.get();
				req.setRequestStatus(RequestStatus.COMPLETED);
				serviceReqRepository.save(req);
			} else {
				throw new NoSuchElementException();
			}
			paymentInfo.setPaymentReportDate(LocalDateTime.now());
			paymentInfo.setName(
							paymentInfo.getReqTrackingId() + "-" + 
							paymentInfo.getTaskerId() + "-" +
							LocalDateTime.now().getDayOfMonth() + "_" + 
							LocalDateTime.now().getMonthValue());
			paymentInfo.setImageByte(file.getBytes());
			paymentInfo.setType(file.getContentType());
			return paymentInformationManager.savePaymentInfo(paymentInfo);
			
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@GetMapping("/paymentinformation/{reqTrackingId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TASKER')")
	@ResponseBody
	public PaymentInformation getPaymentInformation(@PathVariable("reqTrackingId") String reqTrackingId) {
		return paymentInformationManager.findByReqTrackingId(reqTrackingId);
	}

	// 10 minute expiration async method
	public void expireRequests(ServiceRequest serviceRequest) {

		// Get ServiceRequest from db, check if it is STARTED, ACCEPTED, or CANCELLED

		Optional<ServiceRequest> reqOptional = serviceReqRepository.findById(serviceRequest.getId());
		if (reqOptional.isPresent()) {
			ServiceRequest request = reqOptional.get();
			System.out.println("Expiring request: " + request.getTrackingId());
			RequestStatus currentStatus = request.getRequestStatus();
			// performActionOnStatus(currentStatus, request);
			TaskerActionLog taskerActionLog = new TaskerActionLog();

			taskerActionLog.setLogDate(new Date());
			taskerActionLog.setRequestTrackingId(request.getTrackingId());
			if (request.getAssignedTaskerId() != null) {
				taskerActionLog.setTaskerId(request.getAssignedTaskerId());
			}

			switch (currentStatus) {
			case ASSIGNED:
				request.setAssignedTaskerId(null);
				request.setAssignedTime(null);
				request.setRequestStatus(currentStatus.STARTED);
				taskerActionLog.setStatusAction(currentStatus.EXPIRED);
				break;
			case CANCELLED:
				request.setAssignedTaskerId(null);
				request.setAssignedTime(null);
				request.setRequestStatus(currentStatus.STARTED);
				taskerActionLog.setStatusAction(currentStatus.CANCELLED);
				break;
			case IN_PROGRESS:
				taskerActionLog.setStatusAction(currentStatus.IN_PROGRESS);
				request.setRequestStatus(currentStatus.IN_PROGRESS);
				break;
			case COMPLETED:
				taskerActionLog.setStatusAction(currentStatus.COMPLETED);
				break;
			}

			serviceReqRepository.save(request);
			System.out.println(taskerActionLog);
		}
	}

}
