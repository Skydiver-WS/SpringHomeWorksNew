package com.example.springappnewssecure.service.security.impl;

import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.mapper.UserMapper;
import com.example.springappnewssecure.repository.UserRepository;
import com.example.springappnewssecure.security.UserDetailsService;
import com.example.springappnewssecure.service.TokenService;
import com.example.springappnewssecure.service.security.AuthenticationService;
import com.example.springappnewssecure.service.security.JwtTokenService;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Mono<UserResponse> singIn(UserRequest userRequest) {
        log.info("Get token for user {}", userRequest.getUsername());
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()))
                .publishOn(Schedulers.boundedElastic())
                .map(u -> {
                    User user = userRepository.findByUsername(userRequest.getUsername()).orElse(null);
                    UserResponse response = userMapper.userResponseFromUser(user);
                    String token = jwtTokenService.generateToken(new UserDetailsService(user));
                    response.setToken(token);
                    return response;
                })
                .flatMap(usr -> {
                    String token = usr.getToken();
                    return tokenService.tokenSave(token)
                            .thenReturn(usr);
                });
    }
}
