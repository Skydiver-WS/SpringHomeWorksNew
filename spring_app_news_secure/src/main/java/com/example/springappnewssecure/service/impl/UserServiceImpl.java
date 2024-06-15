package com.example.springappnewssecure.service.impl;

import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.mapper.UserMapper;
import com.example.springappnewssecure.repository.UserRepository;
import com.example.springappnewssecure.service.UserService;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAllUsers() {
        log.info("Find all users.");
        return userMapper.listUserResponseFromListUsers(userRepository.findAll());
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Create user: {}", userRequest.toString());
        User user = userRepository.save(userMapper.userFromUserRequest(userRequest));
        log.info("Saving user in db: {}", user.toString());
        return userMapper.userResponseFromUser(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        log.info("Find user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User not found"));
        log.info("User by id {} found.", id);
        userMapper.updateUserFromUserRequest(user, userRequest);
        try {
            userRepository.save(user);
            log.info("User {} update successful", user);
            return userMapper.userResponseFromUser(user);
        } catch (Exception e){
            log.error("Username {} exists", user.getUsername());
            return UserResponse.builder()
                    .errorMessage(e.getMessage())
                    .build();
        }

    }

    @Override
    public void removeUser(String username) {
        log.info("Remove user by username {}", username);
        userRepository.deleteByUsername(username);
        log.info("User {} remove successful", username);
    }
}
