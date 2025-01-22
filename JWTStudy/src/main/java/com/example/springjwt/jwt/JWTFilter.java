package com.example.springjwt.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springjwt.dto.CustomUserDetails;
import com.example.springjwt.entity.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		//request에서 Authorization 헤더를 찾음
		String authorization = request.getHeader("Authorization");
		//Authorization 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {

			System.out.println("token null");
			filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
			return;
		}
		//Bearer 부분 제거 후 순수 토큰만 획득
		String token = authorization.split(" ")[1];
		//토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {

			System.out.println("token expired");
			filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
			return;
		}
		//토큰에서 username과 role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		//userEntity를 생성하여 값 set
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword("temppassword");	// 정확한 비밀번호를 넣을 필요가 없기 때문에 임시 비밀번호 만들기
		userEntity.setRole(role);

		//UserDetails에 회원 정보 객체 담기
		CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

		//스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
