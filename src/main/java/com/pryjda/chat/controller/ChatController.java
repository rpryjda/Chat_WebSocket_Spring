package com.pryjda.chat.controller;

import com.pryjda.chat.service.ChatService;
import com.pryjda.chat.model.request.RequestMessage;
import com.pryjda.chat.model.response.ResponseMessage;
import com.pryjda.chat.utils.components.ConnectedUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    private final ConnectedUsers connectedUsers;

    @Autowired
    public ChatController(ChatService chatService, ConnectedUsers connectedUsers) {
        this.chatService = chatService;
        this.connectedUsers = connectedUsers;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ResponseMessage send(RequestMessage requestMessage, Authentication auth) throws Exception {

        ResponseMessage responseMessage = chatService.createMessage(requestMessage, auth);
        return responseMessage;
    }

    @MessageMapping("/connected")
    @SendTo("/topic/connected-users")
    public ConnectedUsers sendConnectedUsers() throws Exception {

        return connectedUsers;
    }
}
