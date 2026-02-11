package com.project.fitness.dto;

import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.fitness.model.ActivityType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequest {
	

	private String userId;

	@Enumerated(EnumType.STRING)
	private ActivityType type;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private Map<String, Object> additionMetrics;

	private Integer duration;
	private Integer caloriesBurned;

	private LocalDateTime startTime;
	

}
