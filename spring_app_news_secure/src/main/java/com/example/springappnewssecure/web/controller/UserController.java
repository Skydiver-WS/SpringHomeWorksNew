package com.example.springappnewssecure.web.controller;

import com.example.springappnewssecure.web.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/news")
public class UserController {

    @GetMapping
    public Flux<UserResponse> findAll(){

    }
}
