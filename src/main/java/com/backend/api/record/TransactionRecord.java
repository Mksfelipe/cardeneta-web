package com.backend.api.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.domain.model.Transaction;

public record TransactionRecord(BigDecimal amount, LocalDateTime transactionDate, Boolean paid) {
	
	 public TransactionRecord(Transaction transaction) {
	        this(
	        		transaction.getAmount(),
	        		transaction.getTransactionDate(),
	        		transaction.getPaid()
	        );
	    }

}

