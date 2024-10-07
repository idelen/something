package com.jackpot.something.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatInputMessageDto {
	private String message;
	private String sendFrom;
}
