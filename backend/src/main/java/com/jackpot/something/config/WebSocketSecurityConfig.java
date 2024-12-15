package com.jackpot.something.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
			// .nullDestMatcher().authenticated()
			.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
			.simpDestMatchers("/pub/**").authenticated()
			.simpSubscribeDestMatchers("/sub/**").authenticated()
			.anyMessage().permitAll();
	}

	// @Bean
	// public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
	// 	messages
	// 		// .nullDestMatcher().authenticated()
	// 		.simpSubscribeDestMatchers("/user/queue/errors").permitAll()
	// 		.simpDestMatchers("/pub/**").authenticated()
	// 		.simpSubscribeDestMatchers("/sub/**").authenticated()
	// 		.anyMessage().permitAll();
	//
	// 	return messages.build();
	// }

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}
}
