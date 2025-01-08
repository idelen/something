package com.jackpot.something.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
	private Long id;
	private String name;
	private String username;
	private String avatar;
	private Boolean online;
}
