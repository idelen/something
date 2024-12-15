package com.jackpot.something.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String token = accessor.getFirstNativeHeader("Authorization");

			if (token != null && token.startsWith("Bearer ")) {
				token = token.substring(7);
				if (jwtTokenProvider.validateToken(token)) {
					String username = jwtTokenProvider.getUsername(token);
					Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
					accessor.setUser(auth);
				} else {
					throw new JwtException("Invalid token");
				}
			} else {
				throw new IllegalArgumentException("Missing or invalid Authorization header");
			}
		}
		return message;
	}
}
