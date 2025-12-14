package com.cochon.chat.websocket.config.hanler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * 텍스트 기반의 webaocket 메시지를 처리를 수행하는 handler
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	// websocket sessio들을 관리하는 리스트
	private static final ConcurrentHashMap<String, WebSocketSession> clientSession = new ConcurrentHashMap<>();

	/**
	 * [연결 성공] websocket성공적으로 완료되고 websocket 연결이 열려 사용할 준비가 되었다.
	 * - 성공 하였을 경우 session 값 추
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("[+] afterConnectionEstablished :: " + session.getId());
		clientSession.put(session.getId(), session);
	}

	/**
	 * [메시지 전달] 새로운 websocket메시지가 도착했을 때 호출됩니다.
	 * - 전달 받은 메시지를 순회하면서 메시지를 전송
	 * - message.getPayload()를 통해 메시지가 전
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("[+] handleTextMessage :: " + session);
		System.out.println("[+] handleTextMessage :: " + message.getPayload());

		clientSession.forEach((key, value) -> {
			System.out.println("key :: " + key + " value :: " + value);
			if (!key.equals(session.getId())) { // 같은 아이디가 아니면 메시지를 전달합니다.
				try {
					value.sendMessage(message);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
	}

	/**
	 * [소켓 종료 및 전송 오류] websocket연결이 어느 쪽에서든 종료되거나 전송 오류가 발생한 후 호출
	 * - 종료 및 실패하였을 경우 해당 세션을 제거
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
		clientSession.remove(session);
		System.out.println("[+] afterConnectionClosed - Session :: " + session.getId());

	}

}
