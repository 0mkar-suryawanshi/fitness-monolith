package com.project.fitness.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;

@Configuration
public class CorsConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    @PostConstruct
    public void logFrontendUrl() {
        System.out.println(">>> CORS allowed origin: " + frontendUrl);
    }

    @Bean
    public FilterRegistrationBean<Filter> corsFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/*");
        bean.setFilter((request, response, chain) -> {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "https://fintness-monolith.netlify.app");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
            res.setHeader("Access-Control-Allow-Headers", "*");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
                res.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            chain.doFilter(request, response);
        });
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}