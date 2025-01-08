package com.jackpot.something.domain.chat.dto;

import java.time.ZonedDateTime;

import com.jackpot.something.domain.account.dto.AccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
	private Long id;
	private String content;
	private ZonedDateTime timestamp;
	private Boolean unread;
	private AccountDto sender;
}
