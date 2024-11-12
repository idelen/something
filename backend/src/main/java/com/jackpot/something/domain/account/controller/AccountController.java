package com.jackpot.something.domain.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jackpot.something.domain.account.dto.LoginRequest;
import com.jackpot.something.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(accountService.login(loginRequest));
	}
}
