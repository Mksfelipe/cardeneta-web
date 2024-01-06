package com.backend.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.model.Account;
import com.backend.domain.model.Transaction;
import com.backend.domain.repository.AccountRepository;
import com.backend.domain.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	public Page<Transaction> getAll(Pageable pageable) {
		return transactionRepository.findAll(pageable);
	}
	
	@Transactional
	public Transaction save(Long accountId, Transaction transaction) {
		Account account = accountService.findById(accountId);
		account.getTransactions().add(transaction);
		this.calculateTotalAmount(account);
		
		transaction.setAccount(account);
		accountRepository.save(account);
		
		
		return transactionRepository.save(transaction);
	}
	
	private void calculateTotalAmount(Account account) {
	    if (!account.getTransactions().isEmpty()) {
	        BigDecimal balance = account.getTransactions().stream()
	                .filter(transaction -> !transaction.getPaid())
	                .map(Transaction::getAmount)
	                .reduce(BigDecimal::add)
	                .orElse(BigDecimal.ZERO);  // Provide a default value if the optional is empty

	        account.setBalance(balance);
	    }
	}
	
}
