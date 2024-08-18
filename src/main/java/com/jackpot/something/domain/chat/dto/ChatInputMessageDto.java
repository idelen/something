package com.jackpot.something.domain.chat.dto;

import org.springframework.stereotype.Service;

import lombok.Getter;

@Getter
@Service
public class ChatInputMessageDto {
	private String message;
	private String sendFrom;
}
