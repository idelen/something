package com.jackpot.something.domain.chat.service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jackpot.something.domain.account.domain.Account;
import com.jackpot.something.domain.account.dto.AccountDto;
import com.jackpot.something.domain.account.service.AccountService;
import com.jackpot.something.domain.chat.domain.ChatRoom;
import com.jackpot.something.domain.chat.dto.ChatMessageDto;
import com.jackpot.something.domain.chat.dto.ChatRoomCreateRequest;
import com.jackpot.something.domain.chat.dto.ChatRoomItemResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final AccountService accountService;
	private final ChatMessageService chatMessageService;

	private Map<Long, ChatRoom> chatRoomMap = new HashMap<>();
	private Long chatRoomSeq = 1L;

	@PostConstruct
	public void init() {
		createChatRoom(new ChatRoomCreateRequest("채팅방01", "user01"));
		createChatRoom(new ChatRoomCreateRequest("채팅방02", "user01"));
	}

	public List<ChatRoomItemResponse> getChatRoomList() {
		return chatRoomMap.entrySet().stream()
			.sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey()))
			.map(entry -> {
				Long chatRoomId = entry.getKey();
				ChatRoom chatRoom = entry.getValue();

				ChatRoomItemResponse response = new ChatRoomItemResponse();
				response.setId(chatRoomId);
				response.setSender(chatRoom.getCreator());

				AtomicLong seq = new AtomicLong(1L);
				response.setMessages(chatMessageService.getAllChatMessage(chatRoomId).stream()
					.map(inputMessage -> {
						ChatMessageDto dto = new ChatMessageDto();
						dto.setId(seq.getAndIncrement());
						dto.setContent(inputMessage.getMessage());
						dto.setTimestamp(ZonedDateTime.now());
						dto.setUnread(Boolean.TRUE);
						dto.setSender(getAccountDto(inputMessage.getSendFrom()));

						return dto;
					})
				.collect(Collectors.toList()));

				return response;
			})
			.collect(Collectors.toList());
	}

	public void createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setId(chatRoomSeq++);
		chatRoom.setName(chatRoomCreateRequest.getName());

		AccountDto accountDto = getAccountDto(chatRoomCreateRequest.getCreatorName());
		chatRoom.setCreator(accountDto);

		chatRoomMap.put(chatRoom.getId(), chatRoom);
	}

	private AccountDto getAccountDto(String userName) {
		Optional<Account> creator = accountService.findByUserId(userName);
		AccountDto accountDto = new AccountDto();
		if (creator.isPresent()) {
			Account account = creator.get();
			accountDto.setId(account.getId());
			accountDto.setName(account.getUserId());
			accountDto.setUsername(account.getName());
		}
		return accountDto;
	}
}
