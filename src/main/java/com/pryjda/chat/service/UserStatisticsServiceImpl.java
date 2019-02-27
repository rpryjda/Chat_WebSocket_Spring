package com.pryjda.chat.service;

import com.pryjda.chat.utils.components.ConnectedUsers;
import com.pryjda.chat.utils.components.UserStatistics;
import com.pryjda.chat.utils.components.VerbsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final UserStatistics userStatistics;

    private final ConnectedUsers connectedUsers;

    private final VerbCounterService verbCounterService;

    private final VerbsPage verbsPage;

    @Autowired
    public UserStatisticsServiceImpl(UserStatistics userStatistics,
                                     ConnectedUsers connectedUsers,
                                     VerbCounterService verbCounterService,
                                     VerbsPage verbsPage) {
        this.userStatistics = userStatistics;
        this.connectedUsers = connectedUsers;
        this.verbCounterService = verbCounterService;
        this.verbsPage = verbsPage;
    }

    @Override
    public void updateUserStatistics() {

        List<String> users = connectedUsers.getConnectedUsers();
        if (users.size() == 2) {
            userStatistics.setUsernameNo1(users.get(0));
            userStatistics.setUsernameNo2(users.get(1));

            String userOne = userStatistics.getUsernameNo1();
            String userTwo = userStatistics.getUsernameNo2();

            users.stream()
                    .filter(user -> user.equalsIgnoreCase(userOne))
                    .forEach(user -> userStatistics
                            .setVerbOccurrenceNo1(verbCounterService
                                    .countUsedVerbsByUsername(user, verbsPage.getCurrentPage(), verbsPage.getSize())));
            users.stream()
                    .filter(user -> user.equalsIgnoreCase(userTwo))
                    .forEach(user -> userStatistics
                            .setVerbOccurrenceNo2(verbCounterService
                                    .countUsedVerbsByUsername(user, verbsPage.getCurrentPage(), verbsPage.getSize())));
        }
    }
}
