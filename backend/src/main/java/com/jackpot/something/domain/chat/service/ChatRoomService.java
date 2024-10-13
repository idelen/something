package com.jackpot.something.domain.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jackpot.something.domain.chat.dto.ChatRoomCreateRequest;
import com.jackpot.something.domain.chat.dto.ChatRoomItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private Map<Integer, ChatRoomItemResponse> chatRoomMap = new HashMap<>();
	private Integer chatRoomSeq = 1;

	public List<ChatRoomItemResponse> getChatRoomList() {
		return chatRoomMap.entrySet().stream()
			.sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
			.map(Map.Entry::getValue)
			.collect(Collectors.toList());
	}

	public void createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {
		ChatRoomItemResponse chatRoomItemResponse = new ChatRoomItemResponse();
		chatRoomItemResponse.setId(chatRoomSeq++);
		chatRoomItemResponse.setName(chatRoomCreateRequest.getName());
		chatRoomItemResponse.setCreatorName(chatRoomCreateRequest.getCreatorName());

		chatRoomMap.put(chatRoomItemResponse.getId(), chatRoomItemResponse);
	}
}
