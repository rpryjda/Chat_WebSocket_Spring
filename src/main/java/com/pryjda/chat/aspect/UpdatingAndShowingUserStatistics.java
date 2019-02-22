package com.pryjda.chat.aspect;

import com.pryjda.chat.service.UserStatisticsService;
import com.pryjda.chat.utils.components.UserStatistics;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UpdatingAndShowingUserStatistics {

    private final SimpMessageSendingOperations messagingTemplate;

    private final UserStatistics userStatistics;

    private final UserStatisticsService userStatisticsService;

    @Autowired
    public UpdatingAndShowingUserStatistics(SimpMessageSendingOperations messagingTemplate,
                                            UserStatistics userStatistics,
                                            UserStatisticsService userStatisticsService) {
        this.messagingTemplate = messagingTemplate;
        this.userStatistics = userStatistics;
        this.userStatisticsService = userStatisticsService;
    }

    @After("execution( * com.pryjda.chat.controller.VerbPageController.setCurrentPage(..)) ||" +
            "execution( * com.pryjda.chat.controller.UserStatisticsController.setStart(..)) ||" +
            "execution( * com.pryjda.chat.controller.UserStatisticsController.setToday(..)) ||" +
            "execution( * com.pryjda.chat.controller.UserStatisticsController.setTotal(..)) ||" +
            "execution( * com.pryjda.chat.controller.ChatController.send(..))")
    public void updateAndShowUserStatisticsAfterChangingVerbPage() {
        userStatisticsService.updateUserStatistics();
        messagingTemplate.convertAndSend("/topic/statistics", userStatistics);
    }
}
