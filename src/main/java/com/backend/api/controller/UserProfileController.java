package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.TransactionDTO;
import com.backend.api.dto.UserDTO;
import com.backend.domain.model.Transaction;
import com.backend.domain.model.User;
import com.backend.domain.service.TransactionService;
import com.backend.domain.service.UserService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/user/profile")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UserProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping
	@RolesAllowed("USER")
	public UserDTO getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifica se o usuário está autenticado
		if (authentication != null && authentication.isAuthenticated()) {
			User user = userService.findbyCpf(authentication.getName());

			return new UserDTO(user);
		}
		return null;
	}

	@GetMapping("/transanctions")
	public Page<TransactionDTO> getAlltransanctions(Pageable pageable) {

		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifica se o usuário está autenticado
		if (authentication != null && authentication.isAuthenticated()) {
			user = userService.findbyCpf(authentication.getName());

			Page<Transaction> listTransactionPage = transactionService.getAll(user.getAccount().getId(), pageable);

			List<TransactionDTO> transactionRecords = listTransactionPage.getContent().stream()
					.map(TransactionDTO::new).toList();
			return new PageImpl<>(transactionRecords, pageable, listTransactionPage.getTotalElements());
		}
		return null;
	}

}
