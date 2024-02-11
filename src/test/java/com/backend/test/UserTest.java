package com.backend.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.backend.domain.model.Account;
import com.backend.domain.model.Role;
import com.backend.domain.model.User;

import jakarta.validation.ConstraintViolationException;

class UserTest {

	@Test
	void testUserEntity() {
		User user = new User();

		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmail("john.doe@example.com");
		user.setPassword("password123");
		user.setCpf("12345678901");

		Account mockAccount = mock(Account.class);
		user.setAccount(mockAccount);

		Role mockRole = mock(Role.class);
		user.getRoles().add(mockRole);

		assertEquals("John", user.getFirstName());
		assertEquals("Doe", user.getLastName());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals("password123", user.getPassword());
		assertEquals("12345678901", user.getCpf());
		assertTrue(user.getActive());
		assertEquals(mockAccount, user.getAccount());
		assertTrue(user.getRoles().contains(mockRole));
	}

	@Test
	void testUserEntityWithNoRoles() {
		User user = new User();
		user.setFirstName("Jane");
		user.setLastName("Doe");
		user.setEmail("jane.doe@example.com");
		user.setPassword("pass123");
		user.setCpf("98765432109");

		assertNotNull(user.getRoles());
		assertTrue(user.getRoles().isEmpty());
	}

	@Test
	void testUserEntityDeactivate() {
		User user = new User();
		user.setActive(false);

		assertFalse(user.getActive());
	}

	@Test
	void testValidCPF() {
		User user = new User();
		user.setCpf("12345678909");
		assertEquals("12345678909", user.getCpf());
	}

	@Test
	void testRoleAssignment() {
		User user = new User();
		Role role = new Role();
		user.getRoles().add(role);
		assertTrue(user.getRoles().contains(role));
	}

	@Test
	void testAccountAssociation() {
		User user = new User();
		Account account = new Account();
		user.setAccount(account);
		assertEquals(account, user.getAccount());
	}

	@Test
	void testCreationTimestamp() {
		User user = new User();
		assertNotNull(user.getCreated());
	}

	@Test
	void testUpdateTimestamp() {
		User user = new User();
		assertNotNull(user.getUpdated());
	}

	@Test
	void testContact() {
		User user = new User();
		user.setContact("123456789");
		assertEquals("123456789", user.getContact());
	}

}
