package com.example.springchat.controller;

import static java.lang.String.format;

import com.example.springchat.model.ChatMessage;
import com.example.springchat.model.ChatMessage.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    //连接事件不做处理！
    logger.info("Received a new web socket connection.");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) { //websocket 断开事件进行处理！！
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    //从header中拿到Session，从Session中取出之前addUser时放进入的username，通知给同一房间未离开的人！！
    String username = (String) headerAccessor.getSessionAttributes().get("username");
    String roomId = (String) headerAccessor.getSessionAttributes().get("room_id");
    if (username != null) {
      logger.info("User Disconnected: " + username);

      ChatMessage chatMessage = new ChatMessage();
      chatMessage.setType(MessageType.LEAVE);
      chatMessage.setSender(username);

      messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }
  }
}
