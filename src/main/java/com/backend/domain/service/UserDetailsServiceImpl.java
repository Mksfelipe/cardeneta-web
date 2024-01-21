package com.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.domain.model.User;
import com.backend.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		User user = userRepository.findByCpf(cpf)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with cpf: " + cpf));

		return UserDetailsImpl.build(user);
	}

}