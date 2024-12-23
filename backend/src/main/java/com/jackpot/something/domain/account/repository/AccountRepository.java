package com.jackpot.something.domain.account.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jackpot.something.domain.account.domain.Account;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

	private Map<Long, Account> accountMap = new HashMap<>();
	private Long accountKey = 1L;

	public void createNewAccount(String userId, String password) {
		Account account = new Account();
		account.setUserId(userId);
		account.setPassword(password);
		account.setId(accountKey);
		accountMap.put(accountKey++, account);
	}

	public Optional<Account> findByUserId(String userId) {
		return accountMap.values().stream()
			.filter(account -> account.getUserId().equals(userId))
			.findFirst();
	}
}
