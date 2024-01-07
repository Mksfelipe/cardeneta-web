package com.backend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.domain.model.Account;
import com.backend.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	@Query(value = "SELECT a FROM Transaction a WHERE a.account = :account")
	Page<Transaction> findTransactionsByAccountId(Account account, Pageable pageable);
	
}
