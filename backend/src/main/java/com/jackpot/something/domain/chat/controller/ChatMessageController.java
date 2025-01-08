package com.jackpot.something.domain.chat.controller;

import static com.jackpot.something.domain.chat.constant.WebSocketPublishConstants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;
import com.jackpot.something.domain.chat.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

	private final ChatMessageService chatMessageService;

	@MessageMapping(CHAT_ROOM_SEND_MESSAGE_PUB_URL)
	public void publishChatRoomMessage(
		@DestinationVariable Long roomId,
		@Payload ChatInputMessageDto messageDto
	) {
		chatMessageService.sendMessage(roomId, messageDto);
	}

	@ResponseBody
	@GetMapping("/v1.0/chat-room/{roomId}/messages")
	public ResponseEntity<List<ChatInputMessageDto>> getAllChatMessage(
		@PathVariable Long roomId
	) {
		return ResponseEntity.ok(chatMessageService.getAllChatMessage(roomId));
	}
}
