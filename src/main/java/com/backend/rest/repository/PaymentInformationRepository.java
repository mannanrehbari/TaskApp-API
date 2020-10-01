package com.backend.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.rest.entity.PaymentInformation;

@Repository
public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {

}
