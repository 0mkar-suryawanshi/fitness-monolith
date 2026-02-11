package com.project.fitness.service;



import org.springframework.stereotype.Service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserReponse;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository ;
	
	public UserReponse register(RegisterRequest request) {
		
		User user = User.builder()
				.email(request.getEmail())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.password(request.getPassword())
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

	private UserReponse maptoResponse(User savedUser) {
		
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

}
