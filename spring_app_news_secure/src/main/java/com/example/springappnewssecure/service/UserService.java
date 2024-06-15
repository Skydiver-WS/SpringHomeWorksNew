package com.example.springappnewssecure.service;

import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAllUsers();

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void removeUser(String username);
}
