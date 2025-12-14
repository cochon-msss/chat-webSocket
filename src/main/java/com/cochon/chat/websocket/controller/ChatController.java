package com.cochon.chat.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cochon.chat.websocket.dto.ChatMessageDto;

/**
 * websocket 데이터 처리 컨트롤러
 */
@RestController
public class ChatController {

    private final SimpMessagingTemplate template; // 특정 사용자에게 메시지를 보내는데 사용되는 STOMP을 이용한 템플릿

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    /**
     * message 엔드포인트로 데이터와 함께 호출을 하면 "/sub/message"를 수신하는 사용자에게 메시지를 전달한다.
     */
    @MessageMapping("/message")
    public ChatMessageDto send2(@RequestBody ChatMessageDto chatMessageDto) {
        template.convertAndSend("/sub/message", chatMessageDto);
        return chatMessageDto;
    }

}
