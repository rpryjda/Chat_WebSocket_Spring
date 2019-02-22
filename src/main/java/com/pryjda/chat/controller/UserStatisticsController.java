package com.pryjda.chat.controller;

import com.pryjda.chat.model.response.ResponseMessage;
import com.pryjda.chat.service.UserStatisticsService;
import com.pryjda.chat.utils.components.CounterTime;
import com.pryjda.chat.utils.components.UserStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    public UserStatistics getUserStatistics() {
        userStatisticsService.updateUserStatistics();
        return userStatistics;
    }

    @GetMapping("/statistics/start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setStart() {
        LocalDateTime now = LocalDateTime.now();
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(now);
        responseMessage.setContent("SET NEW STATISTICS");
        messagingTemplate.convertAndSend("/topic/message", responseMessage);
        counterTime.setStartTime(now);
    }

    @GetMapping("/statistics/today")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setToday() {
        LocalDateTime now = LocalDateTime.now();
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(now);
        responseMessage.setContent("SET TODAY STATISTICS");
        messagingTemplate.convertAndSend("/topic/message", responseMessage);
        LocalDate dateStart = LocalDate.now();
        counterTime.setStartTime(dateStart.atStartOfDay());
    }

    @GetMapping("/statistics/total")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTotal() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setUsername("info");
        responseMessage.setTime(LocalDateTime.now());
        responseMessage.setContent("SET TOTAL STATISTICS");
        messagingTemplate.convertAndSend("/topic/message", responseMessage);
        counterTime.setStartTime(null);
    }
}
