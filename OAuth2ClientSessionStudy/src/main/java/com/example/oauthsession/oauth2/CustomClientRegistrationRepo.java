package com.example.oauthsession.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomClientRegistrationRepo {

	private final SocialClientRegistration socialClientRegistration;

	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(
			socialClientRegistration.naverClientRegistration(),
			socialClientRegistration.googleClientRegistration()
		);
	}
}
