package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
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
	public RoleHierarchy roleHierarchy() {
		return RoleHierarchyImpl.withDefaultRolePrefix()
			.role("C").implies("B")
			.role("B").implies("A")
			.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/login").permitAll()
				.requestMatchers("/").hasAnyRole("A")	// A, B, C 중 제일 낮은 권한 설정
				.requestMatchers("/manager").hasAnyRole("B")
				.requestMatchers("/admin").hasAnyRole("C")
				.anyRequest().authenticated()
			);

		http
			.formLogin((auth) -> auth.loginPage("/login")
				.loginProcessingUrl("/loginProc")
				.permitAll()
			);

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
			.roles("A")
			.build();

		UserDetails user2 = User.builder()
			.username("user2")
			.password(bCryptPasswordEncoder().encode("1234"))
			.roles("B")
			.build();

		return new InMemoryUserDetailsManager(user1, user2);
	}
}
