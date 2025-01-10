package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 해당 클래스가 스프링 시큐리티에서 관리됨
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests((auth) -> auth	// 상단부터 동작되기 때문에 경로 작성 순서에 유의
				.requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()	// 루트 경로 또는 /login 경로에 대해서 특정한 작업을 진행하고 싶을 때 사용
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")	// **은 와일드카드
				.anyRequest().authenticated()	// 위에서 처리하지 못한 나머지 경로들
			);

		http
			.httpBasic(Customizer.withDefaults());

		// csrf : 사이트 위변조 방지 설정
		http
			.csrf((auth) -> auth.disable());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails user1 = User.builder()
			.username("user1")
			.password(bCryptPasswordEncoder().encode("1234"))
			.roles("ADMIN")
			.build();

		UserDetails user2 = User.builder()
			.username("user2")
			.password(bCryptPasswordEncoder().encode("1234"))
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(user1, user2);
	}
}
