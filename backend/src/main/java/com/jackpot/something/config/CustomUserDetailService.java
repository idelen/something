package com.jackpot.something.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jackpot.something.domain.account.domain.Account;
import com.jackpot.something.domain.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUserId(username).orElseThrow();

		return new User(account.getUserId(), account.getPassword(), new ArrayList<>());
	}
}
