package com.backend.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.api.dto.PaymentDTO;
import com.backend.domain.exception.InsufficientfundsException;
import com.backend.domain.model.Account;
import com.backend.domain.model.Payment;
import com.backend.domain.model.Transaction;
import com.backend.domain.repository.PaymentRepository;
import com.backend.domain.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public void save(Long accountId, PaymentDTO paymentDTO) {
		Account account = accountService.findById(accountId);

		if (paymentDTO.getAmountPaid().compareTo(account.getBalance()) < 0) {
			throw new InsufficientfundsException("insuficiente para realizar o pagamento");
		}

		Payment payment = new Payment();
		modelMapper.map(paymentDTO, payment);

		payment.setChange(payment.getAmountPaid().subtract(account.getBalance()));
		payment.setAccount(account);

		List<Transaction> paidTransactions = transactionService.transactions(account);
		for (Transaction transaction : paidTransactions) {
			transaction.setPaid(true);
			transaction.setPayment(payment);
		}

		account.setBalance(BigDecimal.ZERO);
		accountService.save(account);

		paymentRepository.save(payment);
		transactionRepository.saveAll(paidTransactions);

	}

}
