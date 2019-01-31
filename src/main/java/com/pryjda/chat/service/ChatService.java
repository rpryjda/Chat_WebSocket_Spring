package com.pryjda.chat.service;

import com.pryjda.chat.model.RequestMessage;
import com.pryjda.chat.model.ResponseMessage;
import org.springframework.security.core.Authentication;

public interface ChatService {

    ResponseMessage createMessage(RequestMessage requestMessage, Authentication auth);
}
