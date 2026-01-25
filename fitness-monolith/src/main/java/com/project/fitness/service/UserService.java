package com.project.fitness.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository ;
	
	public User register(RegisterRequest request) {
		
		User user = new User(
				"xx01",
				request.getFirstName(),
				request.getPassword(),
				request.getFirstName(),
				request.getLastName(),
				Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
				Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
				List.of(),
				List.of()
				);
		return userRepository.save(user);
	}

}
