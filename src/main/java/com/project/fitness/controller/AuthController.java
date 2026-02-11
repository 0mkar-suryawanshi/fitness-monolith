package com.project.fitness.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserReponse;

import com.project.fitness.service.UserService;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	} 

	@PostMapping("/register")
	public ResponseEntity<UserReponse> register(@RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(userService.register(registerRequest));
	}

}
