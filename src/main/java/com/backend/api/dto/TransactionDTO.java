package com.backend.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.domain.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

	private Long id;
	private BigDecimal amount;
	private LocalDateTime transactionDate;
	private Boolean paid;
	private Boolean active;

	public TransactionDTO(Transaction transaction) {
		this.id = transaction.getId();
		this.active = transaction.getActive();
		this.transactionDate = transaction.getTransactionDate();
		this.paid = transaction.getPaid();
		this.active = transaction.getActive();
		this.amount = transaction.getAmount();	
	}

	public TransactionDTO(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionDTO() {
	}

}
