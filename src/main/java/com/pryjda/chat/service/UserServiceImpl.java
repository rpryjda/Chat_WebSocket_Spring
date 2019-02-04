package com.pryjda.chat.service;

import com.pryjda.chat.entity.User;
import com.pryjda.chat.model.response.UserResponse;
import com.pryjda.chat.repository.UserRepository;
import com.pryjda.chat.utils.components.ConnectedUsers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SessionRegistry sessionRegistry;

    private final ConnectedUsers connectedUsers;

    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           SessionRegistry sessionRegistry,
                           ConnectedUsers connectedUsers,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.sessionRegistry = sessionRegistry;
        this.connectedUsers = connectedUsers;
        this.mapper = mapper;
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getConnectedUsers() {
        List<String> userNames = connectedUsers.getConnectedUsers();
        return userNames.stream()
                .map(item -> new UserResponse(item))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getLoggedUsers() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<org.springframework.security.core.userdetails.User> securedUsers = allPrincipals.stream()
                .map(item -> (org.springframework.security.core.userdetails.User) item)
                .collect(Collectors.toList());
        return securedUsers.stream()
                .map(item -> mapper.map(item, UserResponse.class))
                .collect(Collectors.toList());
    }
}
