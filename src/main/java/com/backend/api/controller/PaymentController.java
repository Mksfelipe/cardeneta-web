package com.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.PaymentDTO;
import com.backend.domain.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account/{accountId}/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	public void save(@PathVariable Long accountId, @Valid @RequestBody PaymentDTO paymentDTO) {

		paymentService.save(accountId, paymentDTO);
	}
}
