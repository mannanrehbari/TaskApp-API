package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.UsersProfile;

@Repository
public interface UsersProfileRepository extends JpaRepository<UsersProfile, Long> {

}
