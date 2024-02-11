package com.backend.domain.service;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.domain.exception.TransactionNegativeAmountException;
import com.backend.domain.exception.TransactionNotFoundException;
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

	public Page<Transaction> getAll(Long accountId, Pageable pageable) {
		Account account = accountService.findById(accountId);
		
		return transactionRepository.findTransactionsByAccountId(account, pageable);
	}
	
	@Transactional
	public Transaction save(Long accountId, Transaction transaction) {
		
		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new TransactionNegativeAmountException("Transaction amount cannot be negative.");
        }
		
		Account account = accountService.findById(accountId);
		account.getTransactions().add(transaction);
		this.calculateTotalAmount(account);
		
		transaction.setAccount(account);
		accountRepository.save(account);
		
		
		return transactionRepository.save(transaction);
	}
	
	@Transactional
	public void delete(Long accountId, Long transactionId) {
		Account account = accountService.findById(accountId);
		
		boolean transactionExists = account.getTransactions().removeIf(transaction -> Objects.equals(transaction.getId(), transactionId));
		
	    if (!transactionExists) {
	    	throw new TransactionNotFoundException(transactionId);
	    }
	    
	    transactionRepository.deleteById(transactionId);
	    
	    calculateTotalAmount(account);
	}
	
	private void calculateTotalAmount(Account account) {
	    if (!account.getTransactions().isEmpty()) {
	        BigDecimal balance = account.getTransactions().stream()
	                .filter(transaction -> !transaction.getPaid())
	                .map(Transaction::getAmount)
	                .reduce(BigDecimal::add)
	                .orElse(BigDecimal.ZERO);

	        account.setBalance(balance);
	    }
	}
	
}
