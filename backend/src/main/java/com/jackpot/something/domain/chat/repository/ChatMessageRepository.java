package com.jackpot.something.domain.chat.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

	private Map<Integer, List<ChatInputMessageDto>> chatMessageMap = new HashMap<>();

	public void saveChatMessage(Integer chatRoomId, ChatInputMessageDto chatInputMessageDto) {
		List<ChatInputMessageDto> messageList = chatMessageMap.getOrDefault(chatRoomId, new ArrayList<>());
		messageList.add(chatInputMessageDto);
		chatMessageMap.put(chatRoomId, messageList);
	}

	public List<ChatInputMessageDto> getChatMessageList(Integer chatRoomId) {
		return chatMessageMap.getOrDefault(chatRoomId, new ArrayList<>());
	}
}
