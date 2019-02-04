package com.pryjda.chat.service;

import com.pryjda.chat.model.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();
    List<UserResponse> getConnectedUsers();
    List<UserResponse> getLoggedUsers();
}
