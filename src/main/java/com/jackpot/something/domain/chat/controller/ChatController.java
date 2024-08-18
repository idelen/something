package com.jackpot.something.domain.chat.controller;

import static com.jackpot.something.domain.chat.constant.WebSocketPublishConstants.*;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;
import com.jackpot.something.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@MessageMapping(CHAT_ROOM_SEND_MESSAGE_PUB_URL)
	public void publishChatRoomMessage(
		@DestinationVariable Integer roomId,
		@Payload ChatInputMessageDto messageDto
	) {
		chatService.sendMessage(roomId, messageDto);
	}
}
