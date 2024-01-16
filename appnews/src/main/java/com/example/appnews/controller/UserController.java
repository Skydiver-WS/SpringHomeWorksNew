package com.example.appnews.controller;

import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.web.request.dto.user.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private DatabaseUserRepository userRepository;
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest);
    }
}
