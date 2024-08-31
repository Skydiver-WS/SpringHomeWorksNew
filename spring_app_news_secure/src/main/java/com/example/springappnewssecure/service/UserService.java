package com.example.springappnewssecure.service;

import com.example.springappnewssecure.entity.RoleType;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Flux<List<UserResponse>> findAllUsers();

    Mono<UserResponse> createUser(UserRequest userRequest, RoleType roleType);

    Mono<UserResponse> updateUser(UserRequest userRequest);

    Mono<User> findByUsername(String username);


    Mono<Void> removeUser(String username);

}
