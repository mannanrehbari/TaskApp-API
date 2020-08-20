package com.backend.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
	
	Optional<Location> findByCityAndProvinceAndCountry(String city, String province, String country);
	
}
