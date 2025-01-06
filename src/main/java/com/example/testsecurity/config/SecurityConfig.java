package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 해당 클래스가 스프링 시큐리티에서 관리됨
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests((auth) -> auth	// 상단부터 동작되기 때문에 경로 작성 순서에 유의
				.requestMatchers("/", "/login").permitAll()	// 루트 경로 또는 /login 경로에 대해서 특정한 작업을 진행하고 싶을 때 사용
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")	// **은 와일드카드
				.anyRequest().authenticated()	// 위에서 처리하지 못한 나머지 경로들
			);

		return http.build();
	}
}
