package com.project.fitness.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("AuthTokenFilter Called");
		try {
			String jwt = parseJwt(request);

			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String userId = jwtUtils.getUserIdFromToken(jwt);


				Claims claims = jwtUtils.getAllClaims(jwt);
				@SuppressWarnings("unchecked")
				List<String> roles = claims.get("roles", List.class);

				System.out.println("ROLES: " + roles);

				List<GrantedAuthority> authorities = List.of();
				if (roles != null) {

					authorities = roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
							.toList();
				}
				
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId,
						null, authorities);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			filterChain.doFilter(request, response);
		} catch (java.io.IOException e) {
			
			e.printStackTrace();
		} catch (ServletException e) {
			
			e.printStackTrace();
		}
	}

	private String parseJwt(HttpServletRequest request) {
		return jwtUtils.getJwtFromHeader(request);
	}
}
