package com.jackpot.something.domain.chat.service;

import static com.jackpot.something.domain.chat.constant.WebSocketPublishConstants.*;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final SimpMessageSendingOperations messagingTemplate;

	public void sendMessage(Integer roomId, ChatInputMessageDto messageDto) {
		messagingTemplate.convertAndSend(CHAT_ROOM_LATEST_MESSAGE_SUB_URL_FORMAT.formatted(roomId), messageDto);
	}
}
