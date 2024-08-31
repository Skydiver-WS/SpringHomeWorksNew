package com.example.springappnewssecure.service;

import com.example.springappnewssecure.entity.Token;
import reactor.core.publisher.Mono;

public interface TokenService {

    Mono<Void> tokenSave(String token);

    Mono<Token> getToken(Long userId);


}
