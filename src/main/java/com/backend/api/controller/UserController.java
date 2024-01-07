package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.record.UserRecord;
import com.backend.api.record.UserWithAccountRecord;
import com.backend.domain.model.User;
import com.backend.domain.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Cacheable(value = "listUser")
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<UserRecord> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
	    Page<User> listUserPage = userService.getAll(pageable);
	    
	    List<UserRecord> userRecords = listUserPage.getContent()
	            .stream()
	            .map(UserRecord::new).toList();
	    
	    return new PageImpl<>(userRecords, pageable, listUserPage.getTotalElements());
	}
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<UserRecord> findAll(@RequestParam String fullName, @PageableDefault(page = 0, size = 10) Pageable pageable) {
	    Page<User> listUserPage = userService.searchUsersByFullName(fullName, pageable);
	    
	    List<UserRecord> userRecords = listUserPage.getContent()
	            .stream()
	            .map(UserRecord::new).toList();
	    
	    return new PageImpl<>(userRecords, pageable, listUserPage.getTotalElements());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserWithAccountRecord findById(@PathVariable Long id) {
		return new UserWithAccountRecord(userService.findbyId(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserRecord updated(@RequestBody UserRecord userRecord, @PathVariable Long id) {
		User user = userService.update(userRecord, id);
		return new UserRecord(user);
	}
	
	@PutMapping("/{id}/disable")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void disableUser(@PathVariable Long id) {
		userService.disable(id); 
	}
	
	@PutMapping("/{id}/enable")
	@PreAuthorize("ADMIN")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void enableUser(@PathVariable Long id) {
		userService.enable(id); 
	}
	
}
