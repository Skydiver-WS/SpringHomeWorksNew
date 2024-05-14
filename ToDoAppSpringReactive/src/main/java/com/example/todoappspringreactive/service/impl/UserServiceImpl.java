package com.example.todoappspringreactive.service.impl;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.mapper.UserMapper;
import com.example.todoappspringreactive.repository.TaskRepository;
import com.example.todoappspringreactive.repository.UserRepository;
import com.example.todoappspringreactive.service.TaskService;
import com.example.todoappspringreactive.service.UserService;
import com.example.todoappspringreactive.web.response.UserResponse;
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
    private final TaskRepository taskRepository;
    private final UserMapper userMapper;


    @Override
    public Flux<UserResponse> findAll() {
        return userRepository.findAll()
                .map(ur -> userMapper.taskToUserResponse(ur, new Task()));
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
        taskRepository.deleteByAuthorId(id);
        return userRepository.deleteById(id);
    }
}
