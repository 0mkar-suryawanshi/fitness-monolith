package com.project.fitness.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserReponse;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserReponse register(RegisterRequest request) {

		UserRole role = request.getRole() != null ? request.getRole() : UserRole.USER;

		User user = User.builder().email(request.getEmail()).firstName(request.getFirstName())
				.lastName(request.getLastName()).password(passwordEncoder.encode(request.getPassword())).role(role)
				.build();

//		User user = new User(
//				null,
//				request.getFirstName(),
//				request.getPassword(),
//				request.getFirstName(),
//				request.getLastName(),
//				Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//				Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//				List.of(),
//				List.of()
//				);
		User savedUser = userRepository.save(user);
		return maptoResponse(savedUser);
	}

	public UserReponse maptoResponse(User savedUser) {

		UserReponse response = new UserReponse();
		response.setId(savedUser.getId());
		response.setEmail(savedUser.getEmail());
		response.setPassword(savedUser.getPassword());
		response.setFirstName(savedUser.getFirstName());
		response.setLastName(savedUser.getLastName());
		response.setCreatedAt(savedUser.getCreatedAt());
		response.setUpdatedAt(savedUser.getUpdatedAt());
		return response;

	}

	public User authenticate(LoginRequest loginRequest) {

		User user = userRepository.findByEmail(loginRequest.getEmail());

		if (user == null) {
			throw new RuntimeException("Invalid Credetial");
		}

		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid Credetial");
		}
		return user;
	}

}
