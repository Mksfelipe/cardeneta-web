package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.record.TransactionAmountRecord;
import com.backend.api.record.TransactionRecord;
import com.backend.domain.model.Transaction;
import com.backend.domain.service.TransactionService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/account/{accountId}")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping("/transaction")
	@RolesAllowed("ADMIN")
	public Transaction save(@PathVariable Long accountId, @RequestBody TransactionAmountRecord transactionRecord) {
		Transaction transaction = new Transaction(transactionRecord.amount());
		transactionService.save(accountId, transaction);
		
		return null;
	}
	
	@GetMapping("/transaction")
	@RolesAllowed("ADMIN")
	public Page<TransactionRecord> findAll(Pageable pageable) {
	    Page<Transaction> listTransactionPage = transactionService.getAll(pageable);
	    
	    List<TransactionRecord> transactionRecords = listTransactionPage.getContent()
	            .stream()
	            .map(TransactionRecord::new).toList();
	    
	    return new PageImpl<>(transactionRecords, pageable, listTransactionPage.getTotalElements());
	}
}
