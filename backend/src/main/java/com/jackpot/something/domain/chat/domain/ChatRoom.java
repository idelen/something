package com.jackpot.something.domain.chat.domain;

import com.jackpot.something.domain.account.dto.AccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
	private Long id;
	private String name;
	private AccountDto creator;
}
