package com.example.todoappspringreactive.service.impl;

import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.repository.UserRepository;
import com.example.todoappspringreactive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(User user, String id) {
        return userRepository.findById(id).flatMap(us -> {
            if(user.getEmail() != null){
                us.setEmail(user.getEmail());
            }
            if (user.getUsername() != null){
                us.setUsername(user.getUsername());
            }
            return userRepository.save(us);
        });
    }

    @Override
    public Mono<Void> remove(String id) {
        return userRepository.deleteById(id);
    }
}
