package com.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/get")
	public ResponseEntity<String> getMessage() {
//		return ResponseEntity.status(HttpStatus.OK).body("Welecome to Spring Boot family");
		
		return ResponseEntity.status(201).body("Welecome to Spring Boot family");
	}

}
