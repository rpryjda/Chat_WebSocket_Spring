package com.pryjda.chat.controller;

import com.pryjda.chat.model.RequestMessage;
import com.pryjda.chat.model.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public ResponseMessage send(RequestMessage requestMessage) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setContent(LocalDateTime.now().format(formatter) + " " + requestMessage.getContent());
        return responseMessage;
    }
}
