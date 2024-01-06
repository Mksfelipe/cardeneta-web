package com.backend.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.domain.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query("SELECT a FROM Account a WHERE a.id = :id")
	Optional<Account> findById(Long id);
}
