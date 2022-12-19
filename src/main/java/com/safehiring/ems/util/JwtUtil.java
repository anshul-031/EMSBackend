package com.safehiring.ems.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.model.request.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@Slf4j
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.validity}")
	private long tokenValidity;


	@Value("${ems.slack.url}")
	private String slackUrl;

	@Value("${ems.slack.notification.enabled}")
	private boolean slackNotificationEnabled;

	public UserVo getUser(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			UserVo user = new UserVo();
			user.setUsername(body.getSubject());
			Set<String> roles = Arrays.stream(body.get("roles").toString().split(",")).map(String::new)
					.collect(Collectors.toSet());
			user.setRoles(roles);
			return user;
		} catch (Exception e) {
			log.error("getUser from token is failed "+e.getMessage());
		}
		return null;
	}

	public String generateToken(UserVo u) {
		Claims claims = Jwts.claims().setSubject(u.getUsername());
		claims.put("roles", u.getRoles());
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public void validateToken(final String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new InvalidTokenException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new InvalidTokenException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new InvalidTokenException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new InvalidTokenException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new InvalidTokenException("JWT claims string is empty.");
		}
	}

	public void sendSlackNotification( String message) {
		try {
			System.out.println("Going to send slack notification "+slackNotificationEnabled);
			if(slackNotificationEnabled) {
				//String data = "{\"text\":\"" + message + "\"}";
				System.out.println("Data is data "+message);
				HttpClient client = HttpClient.newBuilder().build();
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(slackUrl))
						.POST(HttpRequest.BodyPublishers.ofString(message))
						.build();

				HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
				System.out.println(response.statusCode());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}