package com.jackpot.something.domain.chat.constant;

public class WebSocketPublishConstants {

	public static final String CHAT_ROOM_SEND_MESSAGE_PUB_URL = "/chat-room/{roomId}/send";

	public static final String CHAT_ROOM_LATEST_MESSAGE_SUB_URL_FORMAT = "/sub/chat-room/%s/latest-message";
}
