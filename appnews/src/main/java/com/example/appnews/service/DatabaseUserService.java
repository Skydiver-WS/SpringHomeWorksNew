package com.example.appnews.service;


import com.example.appnews.model.User;
import com.example.appnews.web.request.dto.user.CreateUserRequest;
import com.example.appnews.web.response.user.ListUsersResponse;
import com.example.appnews.web.response.user.UserResponse;

import java.util.List;
import java.util.Optional;

public interface DatabaseUserService {
    User createUser(CreateUserRequest userRequest);
    List<User> findAll();

    ListUsersResponse userResponseFull(ListUsersResponse listUsersResponse);

    Optional<User> findById(Long userId);
    UserResponse findByIdAndUpdate(Long userId, CreateUserRequest userRequest);
    void removeUser(Long userId);
}
