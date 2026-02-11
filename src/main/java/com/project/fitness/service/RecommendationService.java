package com.project.fitness.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

	private final UserRepository userRepository;
	private final ActivityRepository activityRepository;
	private final RecommendationRepository recommendationRepository;

	public Recommendation generateRecommendation(RecommendationRequest request) {

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User Not Found : " + request.getUserId()));

		Activity activity = activityRepository.findById(request.getActivityId())
				.orElseThrow(() -> new RuntimeException("Activity Not Found : " + request.getActivityId()));

		Recommendation recommendation = Recommendation.builder()
				.user(user)
				.activity(activity)
				.improvements(request.getImprovements())
				.suggestions(request.getSuggestion())
				.safety(request.getSafety())
				.type("Fitness Recommendatiom")
				.recommendation("Personalized plan based on your activity")
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).
				build();

		return recommendationRepository.save(recommendation);
	}

	public List<Recommendation> getUserRecommandation(String userId) {
		
		return recommendationRepository.findByUserId(userId);
	}

	public List<Recommendation> getActivityRecommandation(String activityId) {
		
		return recommendationRepository.findByActivityId(activityId);
	}

}
