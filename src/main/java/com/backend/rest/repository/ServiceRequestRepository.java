package com.backend.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long>{
	
	boolean existsByTrackingId(String trackingId);
	Optional<ServiceRequest> findByTrackingId(String trackingId);

}
