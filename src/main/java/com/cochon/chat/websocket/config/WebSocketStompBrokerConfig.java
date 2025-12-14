package com.cochon.chat.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * STOMP를 사용하여 메시지 브로커를ㅅ설정
 * websocket 메시지공브로커의 설정을 정의 메서드 제공
 */
@Configuration // 설정 클래스 지정
@EnableWebSocketMessageBroker // STOMP 메시지 브로커 활성화
public class WebSocketStompBrokerConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 옵션 구성
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 구독(sub) : 접두사로 시작하는 메시지를 브로커가 처리하도록 설정 클라이언트는 이 접두사로 시작하는 주제를 구독하여 메시지를 받을수
        // 있다.
        // 예를 들어, 소켓 통신에서 사용자가 특정 메시지를 받기위해 "/sub" 라는 prefix 기반 메시지 수신을 위해 구독한다.
        config.enableSimpleBroker("/sub"); // 클라이언트가 구독할 수 있는 주제 접두사 설정

        // 발행(pub) : 접두사로 시작하는 메시지를 서버로 보내도록 설정 클라이언트는 이 접두사로 시작하는 주제로 메시지를 보낼수 있다.
        // d예를 들어, 소켓 통신에서 사용자가 특정 메시지를 보내기위해 "/pub" 라는 prefix 기반 메시지 발행을 위해 사용한다.
        config.setApplicationDestinationPrefixes("/pub"); // 클라이언트가 메시지를 보낼 때 사용할 접두사 설정
    }

    /**
     * 각각 특정 URL에 매핑되는 STOMP 엔드포인트를 등록, 선택적으로 SockJS 풀백 옵션을 활성화 구성록
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 웹소켓 연결을 설정하기 위해 사용할 엔드포인트 등록
        registry.addEndpoint("/ws-stomp") // 클라이언트가 연결할 엔드포인트 URL 설정
                // .setAllowedOriginPatterns("*") // 모든 출처 허용 (CORS 설정)
                .setAllowedOriginPatterns("<http://localhost:3000>") // 명시적으로 지정
                .withSockJS(); // SockJS 폴백 옵션 활성화 (웹소켓을 지원하지 않는 브라우저를 위한 대체 옵션)
    }

}
