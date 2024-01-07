package com.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.backend.domain.model.converter.BooleanConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Transaction {
	
	public Transaction(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Transaction() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal amount;

	@CreationTimestamp
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean paid = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean active = true;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	

}
