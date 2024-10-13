package com.jackpot.something.domain.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jackpot.something.domain.chat.dto.ChatRoomCreateRequest;
import com.jackpot.something.domain.chat.dto.ChatRoomItemResponse;
import com.jackpot.something.domain.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@PostMapping("/v1.0/chat-room")
	public ResponseEntity<Void> createChatRoom(
		@RequestBody ChatRoomCreateRequest chatRoomCreateRequest
	) {
		chatRoomService.createChatRoom(chatRoomCreateRequest);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/v1.0/chat-rooms")
	public ResponseEntity<List<ChatRoomItemResponse>> getChatRoomList() {
		return ResponseEntity.ok(chatRoomService.getChatRoomList());
	}
}
