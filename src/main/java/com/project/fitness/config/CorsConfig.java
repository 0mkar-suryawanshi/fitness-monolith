package com.project.fitness.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    // ✅ Dono (local + production) origins allow karne ke liye
    @Value("${frontend.allowed-origins:http://localhost:5173,https://fintness-monolith.netlify.app}")
    private String allowedOriginsStr;

    @PostConstruct
    public void logFrontendUrl() {
        List<String> origins = Arrays.asList(allowedOriginsStr.split(","));
        System.out.println(">>> CORS Allowed Origins: " + origins);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        List<String> origins = Arrays.asList(allowedOriginsStr.split(","));
        configuration.setAllowedOrigins(origins);
        
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);     // agar cookies/auth use kar rahe ho
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);   // sab endpoints pe
        
        return source;
    }

    // ❌ Purana WebMvcConfigurer bean delete kar do (ya comment kar do)
    // Yeh ab zarurat nahi hai kyuki CorsConfigurationSource already kaam kar raha hai
}