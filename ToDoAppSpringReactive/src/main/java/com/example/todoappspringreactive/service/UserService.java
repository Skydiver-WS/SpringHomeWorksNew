package com.example.todoappspringreactive.service;

import com.example.todoappspringreactive.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> findAll();

    Mono<User> findById(String id);

    Mono<User> createUser(User user);

    Mono<User> updateUser(User user, String id);

    Mono<Void> remove(String id);
}
