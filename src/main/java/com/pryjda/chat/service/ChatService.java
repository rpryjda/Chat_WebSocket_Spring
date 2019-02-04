package com.pryjda.chat.service;

import com.pryjda.chat.model.request.RequestMessage;
import com.pryjda.chat.model.response.ResponseMessage;
import org.springframework.security.core.Authentication;

public interface ChatService {

    ResponseMessage createMessage(RequestMessage requestMessage, Authentication auth);
}
