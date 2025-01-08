package com.jackpot.something.domain.account.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jackpot.something.config.JwtTokenProvider;
import com.jackpot.something.domain.account.domain.Account;
import com.jackpot.something.domain.account.dto.LoginRequest;
import com.jackpot.something.domain.account.dto.LoginResponse;
import com.jackpot.something.domain.account.dto.SingUpRequest;
import com.jackpot.something.domain.account.repository.AccountRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;

	@PostConstruct
	public void init() {
		accountRepository.createNewAccount("user01", passwordEncoder.encode("1234"));
	}

	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

		String token = jwtTokenProvider.createToken(authentication.getName());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		return loginResponse;
	}

	public LoginResponse signup(SingUpRequest singupRequest) {
		accountRepository.createNewAccount(singupRequest.getId(), passwordEncoder.encode(singupRequest.getPassword()));

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setId(singupRequest.getId());
		loginRequest.setPassword(singupRequest.getPassword());

		return login(loginRequest);
	}

	public Optional<Account> findByUserId(String userId) {
		return accountRepository.findByUserId(userId);
	}
}
