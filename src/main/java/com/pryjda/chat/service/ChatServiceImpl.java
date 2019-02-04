package com.pryjda.chat.service;

import com.pryjda.chat.entity.Chat;
import com.pryjda.chat.model.request.RequestMessage;
import com.pryjda.chat.model.response.ResponseMessage;
import com.pryjda.chat.repository.ChatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    private final ModelMapper mapper;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, ModelMapper mapper) {
        this.chatRepository = chatRepository;
        this.mapper = mapper;
    }

    @Override
    public ResponseMessage createMessage(RequestMessage requestMessage, Authentication auth) {

        Chat chat = new Chat();
        chat.setContent(requestMessage.getContent());
        chat.setUsername(auth.getName());
        chat.setTime(LocalDateTime.now());

        chatRepository.save(chat);
        ResponseMessage responseMessage = mapper.map(chat, ResponseMessage.class);

        return responseMessage;
    }
}
