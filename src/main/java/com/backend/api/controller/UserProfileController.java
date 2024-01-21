package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.record.TransactionRecord;
import com.backend.api.record.UserWithAccountRecord;
import com.backend.domain.model.Transaction;
import com.backend.domain.model.User;
import com.backend.domain.service.TransactionService;
import com.backend.domain.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;


@RestController
@RequestMapping("api/user/profile")
public class UserProfileController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping
	@RolesAllowed("USER")
    public UserWithAccountRecord getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findbyCpf(authentication.getName());
            
            
            return new UserWithAccountRecord(user);
        }
		return null;
    }
	
	@GetMapping("/transanctions")
	public Page<TransactionRecord> getAlltransanctions(Pageable pageable) {
		
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            user = userService.findbyCpf(authentication.getName());
            
            
            Page<Transaction> listTransactionPage = transactionService.getAll(user.getAccount().getId(), pageable);
    	    
    	    List<TransactionRecord> transactionRecords = listTransactionPage.getContent()
    	            .stream()
    	            .map(TransactionRecord::new).toList();
    	    return new PageImpl<>(transactionRecords, pageable, listTransactionPage.getTotalElements());
        }
        return null;
	}
	
}
