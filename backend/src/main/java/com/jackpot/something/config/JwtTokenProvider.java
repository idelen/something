package com.jackpot.something.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private final long validityInMs = 3600000;

	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMs);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(validity)
			.setExpiration(validity)
			.signWith(key)
			.compact();
	}

	public String getUsername(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
