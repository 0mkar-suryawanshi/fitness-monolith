package com.project.fitness.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated());
		
		
		 http.addFilterBefore(jwtAuthenticationFilter, 
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}


    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
