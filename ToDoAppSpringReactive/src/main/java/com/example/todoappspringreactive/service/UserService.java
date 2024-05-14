package com.example.todoappspringreactive.service;

import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserResponse> findAll();

    Mono<User> findById(String id);

    Mono<User> createUser(User user);

    Mono<User> updateUser(User user, String id);

    Mono<Void> remove(String id);
}
