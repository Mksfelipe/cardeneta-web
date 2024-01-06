package com.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
