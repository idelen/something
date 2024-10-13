package com.jackpot.something.domain.chat.service;

import static com.jackpot.something.domain.chat.constant.WebSocketPublishConstants.*;

import java.util.List;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;
import com.jackpot.something.domain.chat.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

	private final ChatMessageRepository chatMessageRepository;
	private final SimpMessageSendingOperations messagingTemplate;

	public void sendMessage(Integer roomId, ChatInputMessageDto messageDto) {
		chatMessageRepository.saveChatMessage(roomId, messageDto);
		messagingTemplate.convertAndSend(CHAT_ROOM_LATEST_MESSAGE_SUB_URL_FORMAT.formatted(roomId), messageDto);
	}

	public List<ChatInputMessageDto> getAllChatMessage(Integer roomId) {
		return chatMessageRepository.getChatMessageList(roomId);
	}
}
