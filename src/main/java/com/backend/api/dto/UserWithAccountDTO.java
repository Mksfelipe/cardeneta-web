package com.backend.api.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.br.CPF;

import com.backend.domain.model.Account;
import com.backend.domain.model.User;
import com.backend.domain.model.converter.BooleanConverter;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserWithAccountDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDateTime created;
	private LocalDateTime updated;
	private Account account;

	@CPF
	@NotEmpty
	private String cpf;

	private String contact;

	public UserWithAccountDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.created = user.getCreated();
		this.updated = user.getUpdated();
		this.cpf = user.getCpf();
		this.contact = user.getContact();
		this.account = user.getAccount();
	}

	public UserWithAccountDTO() {
	}

}
