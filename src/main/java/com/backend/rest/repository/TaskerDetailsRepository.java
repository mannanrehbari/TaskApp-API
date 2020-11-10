package com.backend.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.TaskerDetails;

@Repository
public interface TaskerDetailsRepository extends JpaRepository<TaskerDetails, Long>{
	
	List<TaskerDetails> findByServiceTypeId(Long serviceTypeId);

}
