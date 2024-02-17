package com.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.domain.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
