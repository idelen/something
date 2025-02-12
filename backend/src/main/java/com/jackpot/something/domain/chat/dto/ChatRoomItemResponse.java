package com.jackpot.something.domain.chat.dto;

import java.util.List;

import com.jackpot.something.domain.account.dto.AccountDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomItemResponse {
	private Long id;
	private AccountDto sender;
	private List<ChatMessageDto> messages;
}
