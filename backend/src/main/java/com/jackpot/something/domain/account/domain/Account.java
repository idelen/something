package com.jackpot.something.domain.account.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	private Long id;
	private String userId;
	private String password;
	private String username;
}
