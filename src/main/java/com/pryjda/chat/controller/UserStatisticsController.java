package com.pryjda.chat.controller;

import com.pryjda.chat.model.response.ResponseMessage;
import com.pryjda.chat.service.UserStatisticsService;
import com.pryjda.chat.utils.components.CounterTime;
import com.pryjda.chat.utils.components.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    private final CounterTime counterTime;

    private final UserStatistics userStatistics;

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public UserStatisticsController(UserStatisticsService userStatisticsService,
                                    CounterTime counterTime,
                                    UserStatistics userStatistics,
                                    SimpMessageSendingOperations messagingTemplate) {
        this.userStatisticsService = userStatisticsService;
        this.counterTime = counterTime;
        this.userStatistics = userStatistics;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/statistics")
    public UserStatistics getUserStatisticsService() {
        userStatisticsService.updateUserStatistics();
        return userStatistics;
    }

    @GetMapping("/statistics/start")
    public void getStart() {
        LocalDateTime timeStart = LocalDateTime.now();
        counterTime.setStartTime(timeStart);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(timeStart);
        responseMessage.setContent("SET NEW STATISTICS");
        messagingTemplate.convertAndSend("/topic/message", responseMessage);
    }

    @GetMapping("/statistics/total")
    public void getTotal() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(LocalDateTime.now());
        responseMessage.setContent("SET TOTAL STATISTICS");
        messagingTemplate.convertAndSend("/topic/message", responseMessage);
        counterTime.setStartTime(null);
    }
}
