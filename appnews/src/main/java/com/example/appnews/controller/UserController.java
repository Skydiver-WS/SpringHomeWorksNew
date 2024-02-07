package com.example.appnews.controller;

import com.example.appnews.mapper.UserMapper;
import com.example.appnews.model.User;
import com.example.appnews.service.DatabaseUserService;
import com.example.appnews.web.request.dto.user.CreateUserRequest;
import com.example.appnews.web.response.user.ListUsersResponse;
import com.example.appnews.web.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final DatabaseUserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<ListUsersResponse> findAll() {
        ListUsersResponse userResponseList = userService.userResponseFull(userMapper.listUsers(userService.findAll()));
        return ResponseEntity.ok(userResponseList);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest userRequest) {
        User newUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UserResponse> editUser(@PathVariable Long id, @RequestBody CreateUserRequest userRequest){
        return ResponseEntity.ok(userService.findByIdAndUpdate(id, userRequest));
    }
}
