package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.record.UserAndAccountRecord;
import com.backend.api.record.UserRecord;
import com.backend.domain.model.User;
import com.backend.domain.service.UserService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@RolesAllowed("ADMIN")
	public Page<UserRecord> findAll(Pageable pageable) {
	    Page<User> listUserPage = userService.getAll(pageable);
	    
	    List<UserRecord> userRecords = listUserPage.getContent()
	            .stream()
	            .map(UserRecord::new).toList();
	    
	    return new PageImpl<>(userRecords, pageable, listUserPage.getTotalElements());
	}
	
	@GetMapping("/{id}")
	@RolesAllowed("ADMIN")
	public UserAndAccountRecord findByID(@PathVariable Long id) {
		return new UserAndAccountRecord(userService.findbyId(id));
	}
	
	@PutMapping("/{id}")
	@RolesAllowed("ADMIN")
	public UserRecord updated(@RequestBody UserRecord userRecord, @PathVariable Long id) {
		User user = userService.update(userRecord, id);
		return new UserRecord(user);
	}
	
	@PutMapping("/{id}/disable")
	@RolesAllowed("ADMIN")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void disableUser(@PathVariable Long id) {
		userService.disable(id); 
	}
	
	@PutMapping("/{id}/enable")
	@RolesAllowed("ADMIN")  
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void enableUser(@PathVariable Long id) {
		userService.enable(id); 
	}
	
	
}
