package com.backend.domain.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.record.UserRecord;
import com.backend.domain.exception.RoleException;
import com.backend.domain.exception.UserExistCpfException;
import com.backend.domain.exception.UserExistEMailException;
import com.backend.domain.exception.UserNotFoundException;
import com.backend.domain.model.Account;
import com.backend.domain.model.ERole;
import com.backend.domain.model.Role;
import com.backend.domain.model.User;
import com.backend.domain.payload.request.SignupRequest;
import com.backend.domain.repository.RoleRepository;
import com.backend.domain.repository.UserRepository;

@Service("clientService")
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	public Page<User> getAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public Page<User> searchUsersByFullName(String fullName, Pageable pageable) {
        return userRepository.searchByFullName(fullName, pageable);
    }
	
	public User findbyId(Long id) {
		return findByUser(id);
	}
	
	public User findbyCpf(String cpf) {
		return findByUserCpf(cpf);
	}
	
	@Transactional
	public User update(UserRecord userUpdated, Long id) {
		User userAtual = findByUser(id);
		
		if (!userAtual.getEmail().equals(userUpdated.email()) && Boolean.TRUE.equals(userRepository.existsByEmail(userUpdated.email()))) {
			throw new UserExistEMailException("Error: Email is already taken!");
		}
		
		BeanUtils.copyProperties(userUpdated, userAtual, "id", "account", "cpf");
		
		return userRepository.save(userAtual);
	}
	
	@Transactional
	public void disable(Long id) {
		User user = findByUser(id);
		user.getAccount().setActive(false);
		user.setActive(false);
	}
	
	@Transactional
	public void enable(Long id) {
		User user = findByUser(id);
		user.getAccount().setActive(true);
		user.setActive(true);
	}

	@Transactional
	public User registerUser(SignupRequest signUpRequest) {

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new UserExistEMailException("Error: Email is already taken!");
		}
		
		if (Boolean.TRUE.equals(userRepository.existsByCpf(signUpRequest.getCpf()))) {
			throw new UserExistCpfException("Error: Cpf is already taken!");
		}

		User user = new User();
		signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		
		mapper.map(signUpRequest, user);

		Account account = new Account();
		user.setAccount(account);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RoleException("Error: Role is not found: " + ERole.ROLE_ADMIN));
					roles.add(adminRole);

					break;
				case "user":
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RoleException("Error: Role is not found: " + ERole.ROLE_USER));
					roles.add(userRole);
					
					break;
				default:
					throw new RoleException("Role not find: " + role);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return user;
	}
	
	private User findByUser(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(String.format("user not found by ID: %d", id)));
	}
	
	private User findByUserCpf(String cpf) {
		return userRepository.findByCpf(cpf)
				.orElseThrow(() -> new UserNotFoundException(String.format("user not found by CPF: %s", cpf)));
	}
	
}
