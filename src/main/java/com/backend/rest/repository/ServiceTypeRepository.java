package com.backend.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.ServiceType;


@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>{
	
	Optional<ServiceType> findByServiceType(String serviceType);

}
