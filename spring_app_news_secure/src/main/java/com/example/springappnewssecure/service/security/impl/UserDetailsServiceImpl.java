package com.example.springappnewssecure.service.security.impl;

import com.example.springappnewssecure.security.UserDetailsService;
import com.example.springappnewssecure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements com.example.springappnewssecure.service.security.UserDetailsService {
    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Finding user by username {}", userService.findByUsername(username).toString());
        return userService.findByUsername(username)
                .flatMap(Mono::just)
                .map(UserDetailsService :: new);
    }
}
