package com.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.exception.AccountNotFoundException;
import com.backend.domain.model.Account;
import com.backend.domain.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public Account save(Account account) {

		return accountRepository.save(account);
	}

	public Account findById(Long id) {
		return findByAccount(id);
	}
	
	private Account findByAccount(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(String.format("Account not found by ID: %d", id)));
	}
}
