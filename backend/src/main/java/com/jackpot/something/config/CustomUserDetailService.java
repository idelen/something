package com.jackpot.something.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jackpot.something.domain.account.domain.Account;
import com.jackpot.something.domain.account.dto.CustomUserDetail;
import com.jackpot.something.domain.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUserId(username).orElseThrow();

		CustomUserDetail customUserDetail = new CustomUserDetail();
		customUserDetail.setId(account.getId());
		customUserDetail.setUserId(account.getUserId());
		customUserDetail.setPassword(account.getPassword());
		return customUserDetail;
	}
}
