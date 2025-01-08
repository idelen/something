package com.jackpot.something.domain.chat.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jackpot.something.domain.chat.dto.ChatInputMessageDto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

	private Map<Long, List<ChatInputMessageDto>> chatMessageMap = new HashMap<>();

	@PostConstruct
	public void init() {
		List<ChatInputMessageDto> inputMessageDtos01 = new ArrayList<>();
		inputMessageDtos01.add(new ChatInputMessageDto("Hi Olivia, I am thinking about taking a vacation", "user1"));
		inputMessageDtos01.add(new ChatInputMessageDto("That sounds like a great idea, Katherine!", "user2"));
		inputMessageDtos01.add(new ChatInputMessageDto("I am considering a trip to the beach", "user3"));
		inputMessageDtos01.add(new ChatInputMessageDto("The beach sounds perfect this time of year", "user2"));
		inputMessageDtos01.add(new ChatInputMessageDto("Yes, I agree. It will be a much-needed break", "user3"));
		chatMessageMap.put(1L, inputMessageDtos01);

		List<ChatInputMessageDto> inputMessageDtos02 = new ArrayList<>();
		inputMessageDtos02.add(new ChatInputMessageDto("Hey Olivia, I was thinking about doing some home improvement work", "user1"));
		inputMessageDtos02.add(new ChatInputMessageDto("That sounds interesting!", "user2"));
		inputMessageDtos02.add(new ChatInputMessageDto("I am planning to repaint the walls and replace the old furniture", "user3"));
		inputMessageDtos02.add(new ChatInputMessageDto("That will definitely give your house a fresh look", "user2"));
		chatMessageMap.put(2L, inputMessageDtos02);
	}

	public void saveChatMessage(Long chatRoomId, ChatInputMessageDto chatInputMessageDto) {
		List<ChatInputMessageDto> messageList = chatMessageMap.getOrDefault(chatRoomId, new ArrayList<>());
		messageList.add(chatInputMessageDto);
		chatMessageMap.put(chatRoomId, messageList);
	}

	public List<ChatInputMessageDto> getChatMessageList(Long chatRoomId) {
		return chatMessageMap.getOrDefault(chatRoomId, new ArrayList<>());
	}
}
