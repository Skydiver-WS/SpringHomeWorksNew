package com.example.appnews.service.impl;

import com.example.appnews.mapper.UserMapper;
import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.service.DatabaseUserService;
import com.example.appnews.web.request.dto.user.CreateUserRequest;
import com.example.appnews.web.response.user.ListUsersResponse;
import com.example.appnews.web.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseUserServiceImpl implements DatabaseUserService {
    private final DatabaseUserRepository userRepository;
    private final DatabaseNewsRepository newsRepository;
    private final UserMapper userMapper;


    @Override
    public User createUser(CreateUserRequest userRequest) {
        return userRepository.save(userMapper.userToMapper(userRequest));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public ListUsersResponse userResponseFull(ListUsersResponse listUsersResponse) {
        return new ListUsersResponse(listUsersResponse.getUserResponseList()
                .stream().peek(userResponse -> userResponse
                        .setNewsList(newsRepository.findNewsByUserId(userResponse.getId())))
                .toList());
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserResponse findByIdAndUpdate(Long userId, CreateUserRequest userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setNikName(userRequest.getNikName());
            userRepository.save(user);
        }
        return userMapper.responseToMapper(user);
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }
}
