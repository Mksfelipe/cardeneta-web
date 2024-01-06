package com.backend.api.record;

import java.time.LocalDateTime;

import com.backend.domain.model.Account;
import com.backend.domain.model.User;

	public record UserAndAccountRecord (
	        Long id,
	        String firstName,
	        String lastName,
	        String email,
	        LocalDateTime created,
	        LocalDateTime updated,
	        String cpf,
	        String contact,
	        Account account
	) {
		
	    public UserAndAccountRecord(User user) {
	        this(
	                user.getId(),
	                user.getFirstName(),
	                user.getLastName(),
	                user.getEmail(),
	                user.getCreated(),
	                user.getUpdated(),
	                user.getCpf(),
	                user.getContact(),
	                user.getAccount()
	        );
	    }
	}
