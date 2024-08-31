package com.example.springappnewssecure.service.impl;

import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.RoleType;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.mapper.UserMapper;
import com.example.springappnewssecure.repository.UserRepository;
import com.example.springappnewssecure.service.UserService;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public Flux<List<UserResponse>> findAllUsers() {
        log.info("Find all users.");
        return Flux.just(userMapper.listUserResponseFromListUsers(userRepository.findAll()));
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest, RoleType roleType) {
        log.info("Create user: {}", userRequest.toString());
        return Mono.fromCallable(() -> {
                    User user = userRepository.save(userMapper.userFromUserRequest(userRequest,
                            List.of(Role.from(roleType))));
                    log.info("Saving user in db: {}", user);
                    return user;
                })
                .subscribeOn(Schedulers.boundedElastic()) // Выполняем в пуле потоков, предназначенном для блокирующих операций
                .map(userMapper::userResponseFromUser);
    }

    @Override
    public Mono<UserResponse> updateUser(UserRequest userRequest) {
        log.info("Find user by id: {}", userRequest.getId());

        return Mono.fromCallable(() -> {
                    User user = userRepository.findById(userRequest.getId()).orElseThrow(() ->
                            new RuntimeException("User not found"));
                    log.info("User by id {} found.", userRequest.getId());
                    userMapper.updateUserFromUserRequest(user, userRequest);
                    userRepository.save(user);
                    log.info("User {} update successful", user);
                    return user;
                })
                .subscribeOn(Schedulers.boundedElastic()) // Выполняем блокирующие операции в пуле потоков для блокирующих задач
                .map(userMapper::userResponseFromUser) // Преобразуем пользователя в UserResponse
                .onErrorResume(e -> {
                    // Логирование ошибки и возврат UserResponse с сообщением об ошибке
                    log.error("Error updating user: {}", e.getMessage());
                    return Mono.just(UserResponse.builder()
                            .errorMessage(e.getMessage())
                            .build());
                });
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return Mono.fromCallable(() -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> removeUser(String username) {
        log.info("Remove user by username {}", username);
        log.info("User {} remove successful", username);
        return Mono.fromRunnable(() -> userRepository.deleteByUsername(username))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(unused -> log.info("User {} removed successfully", username))
                .then();
    }
}
