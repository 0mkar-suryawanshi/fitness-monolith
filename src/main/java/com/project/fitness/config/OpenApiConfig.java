package com.project.fitness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customAPI() {
		return new OpenAPI().info(new Info().title("Fistness Tracking API").version("v1.0").description("Production Grade API's").contact(new Contact()
				.name("Sury0m")
				.url("https://github.com/0mkar-suryawanshi")
				.email("suryawanshio639@gmail.com"))
				.license(new License()
						.name("Apache 2.0")
						.url("https://github.com/0mkar-suryawanshi")));
	}
}
