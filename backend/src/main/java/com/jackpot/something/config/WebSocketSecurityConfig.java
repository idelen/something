package com.jackpot.something.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

	@Bean
	public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
		messages
			// .nullDestMatcher().authenticated()
			.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
			.simpDestMatchers("/pub/**").authenticated()
			.simpSubscribeDestMatchers("/sub/**").authenticated()
			.anyMessage().permitAll();

		return messages.build();
	}
}
