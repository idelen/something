package com.jackpot.something.domain.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jackpot.something.domain.account.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUserId(String userId);
}
