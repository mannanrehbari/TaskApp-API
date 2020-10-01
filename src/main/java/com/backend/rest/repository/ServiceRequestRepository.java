package com.backend.rest.repository;

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
	
	// search criteria combinations
	// locationId, serviceTypeId, requestStatus
	List<ServiceRequest> findByLocationId(Integer locationId);
	List<ServiceRequest> findByServiceTypeId(Integer serviceTypeId);
	List<ServiceRequest> findByRequestStatus(RequestStatus requestStatus);
	
	List<ServiceRequest> findByLocationIdAndServiceTypeId(Integer locationId, Integer serviceTypeId);
	List<ServiceRequest> findByLocationIdAndRequestStatus(Integer locationId, RequestStatus requestStatus);
	List<ServiceRequest> findByServiceTypeIdAndRequestStatus(Integer serviceTypeId, RequestStatus requestStatus);
	
	List<ServiceRequest> findByLocationIdAndServiceTypeIdAndRequestStatus(Integer locationId, Integer serviceTypeId, RequestStatus requestStatus);

}
