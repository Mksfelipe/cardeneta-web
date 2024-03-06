package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.TransactionDTO;
import com.backend.domain.model.Transaction;
import com.backend.domain.service.TransactionService;

@RestController
@RequestMapping("/api/account/{accountId}/transaction")
@CrossOrigin(origins = "https://cardeneta-web-production.up.railway.app/", maxAge = 3600, allowCredentials="true")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void save(@PathVariable Long accountId, @RequestBody TransactionDTO transactionDTO) {
		transactionService.save(accountId, transactionDTO);
		
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<TransactionDTO> findAll(@PathVariable Long accountId, Pageable pageable) {
	    Page<Transaction> listTransactionPage = transactionService.getAll(accountId, pageable);
	    
	    List<TransactionDTO> transactionsDTO = listTransactionPage.getContent()
	            .stream()
	            .map(TransactionDTO::new).toList();
	    
	    return new PageImpl<>(transactionsDTO, pageable, listTransactionPage.getTotalElements());
	}
	
	@DeleteMapping("/{transactionId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(@PathVariable Long accountId, @PathVariable Long transactionId) {
		transactionService.delete(accountId, transactionId);
	}
}
