package com.example.springappnewssecure.service.impl;

import com.example.springappnewssecure.applicationparametr.TokenParameter;
import com.example.springappnewssecure.entity.Token;
import com.example.springappnewssecure.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final TokenParameter tokenParameter;


    private final ReactiveRedisTemplate<Long, Token> reactiveRedisTemplate;
    @Override
    public Mono <Void> tokenSave(String token) {
        log.info("Start save token");
        Token tokenDto = Token.builder()
                .token(token)
                .build();
        return reactiveRedisTemplate.opsForValue()
                .set(6L, tokenDto, tokenParameter.getTokenExpiration())
                .doOnSuccess(aVoid -> log.info("Token save successful"))
                .doOnError(error -> log.error("Token save failed", error))
                .then();
    }

    @Override
    public Mono<Token> getToken(Long userId) {
        return reactiveRedisTemplate.opsForValue().get(userId);
    }
}
