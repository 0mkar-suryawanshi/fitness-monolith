package com.project.fitness.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fitness.dto.LoginResponse;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserReponse;
import com.project.fitness.model.User;
import com.project.fitness.security.JwtUtils;
import com.project.fitness.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

	private final UserService userService;
	private final JwtUtils jwtUtils;

	public AuthController(UserService userService, JwtUtils jwtUtils) {
		this.userService = userService;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/register")
	public ResponseEntity<UserReponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(userService.register(registerRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody com.project.fitness.dto.LoginRequest loginRequest) {
		try {
			User user = userService.authenticate(loginRequest);

			String token = jwtUtils.generateToken(user.getId(), user.getRole().name());

			return ResponseEntity.ok(new LoginResponse(token, userService.maptoResponse(user)));

		} catch (AuthenticationException e) {
			e.printStackTrace();

			return ResponseEntity.status(401).build();
		}
	}
}
