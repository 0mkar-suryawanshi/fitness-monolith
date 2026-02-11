package com.project.fitness.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {
	 private final ActivityRepository activityRepository;
	 private final UserRepository userRepository;
	
	 
	public ActivityResponse trackActivity(ActivityRequest activityRequest) {
		
		User user = userRepository.findById(activityRequest.getUserId()).orElseThrow(()-> new RuntimeException("Invalid User : "+activityRequest.getUserId())); 
		
		Activity activity = Activity.builder()
				.user(user)
				.type(activityRequest.getType())
				.duration(activityRequest.getDuration())
				.caloriesBurned(activityRequest.getCaloriesBurned())
				.startTime(activityRequest.getStartTime())
				.additionMetrics(activityRequest.getAdditionMetrics())
				.build();
		
		Activity savedActivity =  activityRepository.save(activity);
		
		return mapToResponse(savedActivity);
				
	}


	private ActivityResponse mapToResponse(Activity activity) {
		
		ActivityResponse response = new ActivityResponse();
		response.setId(activity.getId());
		response.setUserId(activity.getUser().getId());
		response.setType(activity.getType());
		response.setDuration(activity.getDuration());
		response.setCaloriesBurned(activity.getCaloriesBurned());
		response.setStarTime(activity.getStartTime());
		response.setAdditionMetrics(activity.getAdditionMetrics());
		response.setCreatedAt(activity.getCreatedAt());
		response.setUpdatedAt(activity.getUpdatedAt());
		
		return response;
		
		
	}


	public List<ActivityResponse> getUserActivities(String userId) {
		
		List<Activity> activityList = activityRepository.findByUserId(userId);
		
		return activityList.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

}
