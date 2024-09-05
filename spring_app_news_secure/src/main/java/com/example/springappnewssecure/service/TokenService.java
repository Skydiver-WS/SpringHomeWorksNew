package com.example.springappnewssecure.service;

import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.Token;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TokenService {

    Mono<Void> tokenSave(Long id, String role, String token);

    Mono<Token> getToken(Long userId);

    Mono<Void> deleteToken(Long userId);


}
