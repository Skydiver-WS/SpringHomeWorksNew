package com.example.springappnewssecure.web.controller;

import com.example.springappnewssecure.aop.LoggingController;
import com.example.springappnewssecure.entity.RoleType;
import com.example.springappnewssecure.service.UserService;
import com.example.springappnewssecure.service.security.AuthenticationService;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    @LoggingController
    public Flux<List<UserResponse>> findAll(ServerHttpRequest serverHttpRequest) {
        return userService.findAllUsers();
    }

    @PostMapping
    @LoggingController
    public Mono<ResponseEntity<UserResponse>> createUser(ServerHttpRequest serverHttpRequest,
                                                         @RequestBody UserRequest userRequest,
                                                         @RequestParam RoleType roleType) {
        return userService.createUser(userRequest, roleType)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/sing-in")
    @LoggingController
    public Mono<ResponseEntity<UserResponse>> singIn(ServerHttpRequest serverHttpRequest, @RequestBody UserRequest userRequest) {
        return authenticationService.singIn(userRequest)
                .map(ResponseEntity::ok);
    }

    @PutMapping
    @LoggingController
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public Mono<ResponseEntity<UserResponse>> updateUser(ServerHttpRequest serverHttpRequest, @RequestParam Long id,
                                                         @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest).map(ResponseEntity::ok);
    }

    @DeleteMapping
    @LoggingController
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Mono<ResponseEntity<Void>> deleteUser(ServerHttpRequest serverHttpRequest, @RequestParam Long id) {
        return userService.removeUser(id).thenReturn(ResponseEntity.noContent().build());
    }
}
