package com.cochon.chat.websocket.dto;

import lombok.Data;

/**
 * 채팅 메시지 DTO
 */
@Data
public class ChatMessageDto {
    private String content;
    private String sender;

    public ChatMessageDto(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

}
