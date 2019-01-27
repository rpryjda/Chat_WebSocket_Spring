package com.pryjda.chat.controller;

import com.pryjda.chat.Service.ChatServiceImpl;
import com.pryjda.chat.model.RequestMessage;
import com.pryjda.chat.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatServiceImpl chatService;

    @Autowired
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ResponseMessage send(RequestMessage requestMessage, Authentication auth) throws Exception {

        ResponseMessage responseMessage = chatService.createMessage(requestMessage, auth);
        return responseMessage;
    }
}