package com.backend.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/TESTE")
public class ControllerTEST {

	
	@GetMapping
	public String test() {
		return "TESTE";
	}
	
}
