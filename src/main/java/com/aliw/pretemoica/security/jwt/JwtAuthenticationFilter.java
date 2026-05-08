package com.aliw.pretemoica.security.jwt;

import com.aliw.pretemoica.service.CustomUserDetailsService;
import com.aliw.pretemoica.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final List<String> PUBLIC_PATHS = List.of(
			"/api/auth/**",
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/webjars/**"
	);

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public JwtAuthenticationFilter(
			JwtService jwtService,
			CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return PUBLIC_PATHS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		try {
			String username = jwtService.extractUsername(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication =
							new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities());
					authentication.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
					return;
				}
			}

			filterChain.doFilter(request, response);
		} catch (IllegalArgumentException ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
		}
	}
}

