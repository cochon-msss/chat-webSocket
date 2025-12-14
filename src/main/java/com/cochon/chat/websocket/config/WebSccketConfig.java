package com.cochon.chat.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.cochon.chat.websocket.config.hanler.ChatWebSocketHandler;

import lombok.RequiredArgsConstructor;

/**
 * websocket 최초 연결을 위해 구성하는 환경 구성 파일 클래
 */
@Configuration
@EnableWebSocket
// websocket 사용을 활성화하고 @Configuration 어노테이션을 통해 환경파일임을 지정
// 이 어노테이션을 사용하면 spring 애플리케이션에서 websockt기능을 사용 가
@RequiredArgsConstructor
public class WebSccketConfig implements WebSocketConfigurer {

	private final ChatWebSocketHandler chatWebSocketHandler;

	/**
	 * 클라이언트에서 /ws-stomp 경로로 websocket연결을 시도하면 chatwebsockehandler으로 연결을 처리하게 핸들러 등록
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("[+] 최초 WebSocket 연결을 위한 등록 Handler");
		registry
				// 클라이언트에서 웹 소켓 연결을 위해 "ws-stomp"라는 엔드포인트로 연결을 시도하면 ChatWebSocketHandler 클래스에서
				// 이를 처리합니다.
				.addHandler(chatWebSocketHandler, "ws-stomp")
				// 접속 시도하는 모든 도메인 또는 IP 접속 허
				.setAllowedOrigins("*");

	}

}
