package com.backend.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/TESTE")
@RestController
public class ControllerTEST {

	
	@GetMapping
	public String test() {
		return "TESTE";
	}
	
}
