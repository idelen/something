package com.jackpot.something.domain.account.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jackpot.something.config.JwtTokenProvider;
import com.jackpot.something.domain.account.dto.LoginRequest;
import com.jackpot.something.domain.account.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

		String token = jwtTokenProvider.createToken(authentication.getName());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		return loginResponse;
	}
}
