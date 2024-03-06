package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.dto.UserDTO;
import com.backend.domain.model.User;
import com.backend.domain.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://cardeneta-angular-36wdkhdr3-felipes-projects-baa7e28d.vercel.app", maxAge = 3600, allowCredentials = "true")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<UserDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
	    Page<User> listUserPage = userService.getAll(pageable);
	    
	    List<UserDTO> usersDTO = listUserPage.getContent()
	            .stream()
	            .map(UserDTO::new).toList();
	    
	    return new PageImpl<>(usersDTO, pageable, listUserPage.getTotalElements());
	}
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<UserDTO> findAll(@RequestParam String fullName, @PageableDefault(page = 0, size = 10) Pageable pageable) {
	    Page<User> listUserPage = userService.searchUsersByFullName(fullName, pageable);
	    
	    List<UserDTO> usersDTO = listUserPage.getContent()
	            .stream()
	            .map(UserDTO::new).toList();
	    
	    return new PageImpl<>(usersDTO, pageable, listUserPage.getTotalElements());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserDTO findById(@PathVariable Long id) {
		return new UserDTO(userService.findbyId(id));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserDTO updated(@RequestBody UserDTO userDTO, @PathVariable Long id) {
		User user = userService.update(userDTO, id);
		return new UserDTO(user);
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
