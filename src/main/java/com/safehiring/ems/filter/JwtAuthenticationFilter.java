package com.safehiring.ems.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.model.request.UserVo;
import com.safehiring.ems.service.impl.UserDetailServiceImpl;
import com.safehiring.ems.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	private final JwtUtil jwtUtil;


	private final UserDetailServiceImpl userAuthService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer")) {
			throw new InvalidTokenException("No JWT token found in the request headers");
		}

		String token = header.substring(7);

		// Optional - verification
		jwtUtil.validateToken(token);

		UserVo userVo = jwtUtil.getUser(token);

		UserDetails userDetails = userAuthService.loadUserByUsername(userVo.getUsername());

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}

		filterChain.doFilter(request, response);
	}

}