package com.jackpot.something.domain.account.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jackpot.something.domain.account.dto.AccountLoginDto;
import com.jackpot.something.domain.account.dto.LoginRequest;
import com.jackpot.something.domain.account.dto.SingUpRequest;
import com.jackpot.something.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(accountService.login(loginRequest));
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@RequestBody SingUpRequest singupRequest) {
		return ResponseEntity.ok(accountService.signup(singupRequest));
	}

	@GetMapping("/auth/me")
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
		}

		AccountLoginDto loginDto = new AccountLoginDto();
		loginDto.setUserName(userDetails.getUsername());

		return ResponseEntity.ok(loginDto);
	}
}
