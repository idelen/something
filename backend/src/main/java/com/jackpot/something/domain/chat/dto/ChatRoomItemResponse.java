package com.jackpot.something.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomItemResponse {
	private Integer id;
	private String name;
	private String creatorName;
}
