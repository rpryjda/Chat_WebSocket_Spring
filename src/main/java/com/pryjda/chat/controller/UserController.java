package com.pryjda.chat.controller;

import com.pryjda.chat.model.response.UserResponse;
import com.pryjda.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/connected")
    public List<UserResponse> getConnectedUsers() {
        return userService.getConnectedUsers();
    }

    @GetMapping("/users/logged")
    public List<UserResponse> getLoggedUsers() {
        return userService.getLoggedUsers();
    }
}
