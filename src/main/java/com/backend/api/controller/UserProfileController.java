package com.backend.api.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.AccountDTO;
import com.backend.api.dto.TransactionDTO;
import com.backend.api.dto.UserDTO;
import com.backend.domain.model.Transaction;
import com.backend.domain.model.User;
import com.backend.domain.service.AccountService;
import com.backend.domain.service.TransactionService;
import com.backend.domain.service.UserService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("api/user/profile")
public class UserProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@RolesAllowed("USER")
	public UserDTO getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountDTO accountDTO = new AccountDTO();
		
		if (authentication != null && authentication.isAuthenticated()) {
			User user = userService.findbyCpf(authentication.getName());
			accountDTO.setBalanceWeek(accountService.balanceWeek(user.getId()));
			accountDTO.setBalanceMonth(accountService.balanceMonth(user.getId()));
			
			
			modelMapper.map(user.getAccount(), accountDTO);
			UserDTO userDTO = new UserDTO(user);
			userDTO.setAccount(accountDTO);
			
			return userDTO;
		}
		return null;
	}

	@GetMapping("/transanctions")
	@RolesAllowed("USER")
	public Page<TransactionDTO> getAlltransanctions(@PageableDefault(size = 10) Pageable pageable) {

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
