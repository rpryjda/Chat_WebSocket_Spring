package com.pryjda.chat.listener;

import com.pryjda.chat.utils.components.ConnectedUsers;
import com.pryjda.chat.model.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

@Component
public class EventListenerWebSocket {

    private final SimpMessageSendingOperations messagingTemplate;

    private final ConnectedUsers connectedUsers;

    @Autowired
    public EventListenerWebSocket(SimpMessageSendingOperations messagingTemplate, ConnectedUsers connectedUsers) {
        this.messagingTemplate = messagingTemplate;
        this.connectedUsers = connectedUsers;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(LocalDateTime.now());
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        responseMessage.setContent(sha.getUser().getName() + " has joined the chat");

        messagingTemplate.convertAndSend("/topic/message", responseMessage);

        connectedUsers.getConnectedUsers().add(sha.getUser().getName());
        messagingTemplate.convertAndSend("/topic/connected-users", connectedUsers);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(LocalDateTime.now());
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        responseMessage.setContent(sha.getUser().getName() + " has left the chat");

        messagingTemplate.convertAndSend("/topic/message", responseMessage);

        connectedUsers.getConnectedUsers().remove(sha.getUser().getName());
        messagingTemplate.convertAndSend("/topic/connected-users", connectedUsers);
    }

}
