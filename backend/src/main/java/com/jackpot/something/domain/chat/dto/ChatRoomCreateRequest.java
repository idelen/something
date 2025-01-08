package com.jackpot.something.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequest {
	private String name;
	private String creatorName;

	public ChatRoomCreateRequest(String name, String creatorName) {
		this.name = name;
		this.creatorName = creatorName;
	}
}
