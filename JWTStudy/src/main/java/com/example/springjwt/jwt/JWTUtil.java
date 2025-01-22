package com.example.springjwt.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private SecretKey secretKey;

	public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
		this.secretKey = new SecretKeySpec(
			secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	// 검증 메소드
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token).getPayload()
			.get("username", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token).getPayload()
			.get("role", String.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token).getPayload()
			.getExpiration().before(new Date());
	}

	// 토큰 생성 및 응답(토큰 생성 메소드)
	public String createJwt(String username, String role, Long expireMs) {
		return Jwts.builder()
			.claim("username", username)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis()))	// 현재 발행 시간
			.expiration(new Date(System.currentTimeMillis() + expireMs))	// 언제 소멸될 것인지
			.signWith(secretKey)	// 시그니처를 만들어서 암호화를 진행
			.compact();
	}
}
