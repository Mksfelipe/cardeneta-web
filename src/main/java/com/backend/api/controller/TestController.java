package com.backend.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/TESTE")
public class TestController {

	@GetMapping
	String get() {
		return "TESTE";
	}
	
}
