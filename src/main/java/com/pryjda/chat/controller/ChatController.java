package com.pryjda.chat.controller;

import com.pryjda.chat.service.ChatService;
import com.pryjda.chat.model.request.RequestMessage;
import com.pryjda.chat.model.response.ResponseMessage;
import com.pryjda.chat.service.UserStatisticsService;
import com.pryjda.chat.service.VerbService;
import com.pryjda.chat.utils.components.ConnectedUsers;
import com.pryjda.chat.utils.components.UserStatistics;
import com.pryjda.chat.utils.components.VerbsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    private final ConnectedUsers connectedUsers;

    private final UserStatistics userStatistics;

    private final UserStatisticsService userStatisticsService;

    private final VerbService verbService;

    private final VerbsPage verbsPage;

    @Autowired
    public ChatController(ChatService chatService,
                          ConnectedUsers connectedUsers,
                          UserStatistics userStatistics,
                          UserStatisticsService userStatisticsService,
                          VerbService verbService,
                          VerbsPage verbsPage) {
        this.chatService = chatService;
        this.connectedUsers = connectedUsers;
        this.userStatistics = userStatistics;
        this.userStatisticsService = userStatisticsService;
        this.verbService = verbService;
        this.verbsPage = verbsPage;
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

    @MessageMapping("/verbs")
    @SendTo("/topic/verbs")
    public VerbsPage sendVerbsDetails() throws Exception {

        verbsPage.setCurrentPage(0);
        verbsPage.setSize(10);
        verbsPage.setVerbs(verbService.getPaginatedVerbs(verbsPage.getCurrentPage(), verbsPage.getSize()));
        verbsPage.setMaxPage(verbService.getLastPageForPaginatedVerbs(verbsPage.getSize()));
        return verbsPage;
    }

    @MessageMapping("/statistics")
    @SendTo("/topic/statistics")
    public UserStatistics sendUsersStatistics() throws Exception {

        userStatisticsService.updateUserStatistics();
        return userStatistics;
    }
}
