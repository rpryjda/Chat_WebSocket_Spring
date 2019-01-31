package com.pryjda.chat.controller;

import com.pryjda.chat.utils.components.ConnectedUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final ConnectedUsers connectedUsers;

    @Autowired
    public UserController(ConnectedUsers connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @GetMapping("/connected-users")
    public List<String> getConnectedUsers() {
        return connectedUsers.getConnectedUsers();
    }
}
