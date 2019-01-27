package com.pryjda.chat.Service;

import com.pryjda.chat.model.RequestMessage;
import com.pryjda.chat.model.ResponseMessage;
import org.springframework.security.core.Authentication;

public interface ChatService {

    ResponseMessage createMessage(RequestMessage requestMessage, Authentication auth);
}
