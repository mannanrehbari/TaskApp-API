package com.backend.rest.security.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.rest.security.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	@Query(
		value = "SELECT ur.user_id " + 
				"FROM user_roles ur " + 
				"INNER JOIN roles r " + 
				"ON ur.role_id = r.id " + 
				"WHERE r.id = 3;",
		nativeQuery=true)
	List<Long> findAllTaskerIds();
	
	
	@Modifying
	@Transactional
	@Query(
		value = "INSERT INTO tasker_service (taskerId, serviceId) " +
				"values (:taskerId, :serviceId)", 
		nativeQuery = true)
	void insertTaskerServiceType(
			@Param("taskerId") Long taskerId, 
			@Param("serviceId") Long serviceId);
	
	@Query(
		value = "SELECT taskerId, serviceId FROM tasker_service",
		nativeQuery = true)
	public List<Object []> findTaskerServiceMapping();
	
	
	
}
