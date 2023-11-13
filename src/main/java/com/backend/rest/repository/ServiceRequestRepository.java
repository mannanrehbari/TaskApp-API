package com.backend.rest.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.ServiceRequest;
import com.backend.rest.enums.RequestStatus;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>{
	
	boolean existsByTrackingId(String trackingId);
	Optional<ServiceRequest> findByTrackingId(String trackingId);
	
	List<ServiceRequest> findByAssignedTaskerId(Long assignedTaskerId);
	
	List<ServiceRequest> findBySeekerEmail(String email);
	
	// search criteria combinations
	// locationId, serviceTypeId, requestStatus
	List<ServiceRequest> findByLocationIdAndRequestDateBetween(Integer locationId, Date minDate, Date maxDate);
	List<ServiceRequest> findByServiceTypeIdAndRequestDateBetween(Integer serviceTypeId, Date minDate, Date maxDate);
	List<ServiceRequest> findByRequestStatusAndRequestDateBetween(RequestStatus requestStatus, Date minDate, Date maxDate);
	
	List<ServiceRequest> findByLocationIdAndServiceTypeIdAndRequestDateBetween(Integer locationId, Integer serviceTypeId, Date minDate, Date maxDate);
	List<ServiceRequest> findByLocationIdAndRequestStatusAndRequestDateBetween(Integer locationId, RequestStatus requestStatus, Date minDate, Date maxDate);
	List<ServiceRequest> findByServiceTypeIdAndRequestStatusAndRequestDateBetween(Integer serviceTypeId, RequestStatus requestStatus, Date minDate, Date maxDate);
	
	List<ServiceRequest> findByLocationIdAndServiceTypeIdAndRequestStatusAndRequestDateBetween(Integer locationId, Integer serviceTypeId, RequestStatus requestStatus, Date minDate, Date maxDate);
	List<ServiceRequest> findAllByRequestDateBetween(Date minDate, Date maxDate);

}
