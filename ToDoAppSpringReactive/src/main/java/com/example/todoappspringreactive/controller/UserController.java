package com.example.todoappspringreactive.controller;

import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Flux<ResponseEntity<User>> getUser() {
        return userService.findAll().map(ResponseEntity::ok);
    }

    @GetMapping("/get-by-id")
    public Mono<ResponseEntity<User>> getUserById(@RequestParam String id) {
        return userService.findById(id).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        return userService.createUser(user).map(ResponseEntity::ok);
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody User user, @RequestParam String id) {
        return userService.updateUser(user, id).map(ResponseEntity::ok);
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteUser(@RequestParam String id) {
        return userService.remove(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
