package com.example.springappnewssecure.service.impl;

import com.example.springappnewssecure.applicationparametr.TokenParameter;
import com.example.springappnewssecure.dto.TokenDto;
import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.Token;
import com.example.springappnewssecure.mapper.TokenMapper;
import com.example.springappnewssecure.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final TokenParameter tokenParameter;
    private final TokenMapper tokenMapper;


    private final ReactiveRedisTemplate<Long, String> reactiveRedisTemplate;
    @Override
    @SneakyThrows
    public Mono <Void> tokenSave(Long id, String role, String token) {
        log.info("Start save token");
        ObjectMapper objectMapper = new ObjectMapper();

        TokenDto tokenDto = TokenDto.builder()
                .id(id)
                .token(token)
                .role(role)
                .build();
        String tokenObjectMapper = objectMapper.writeValueAsString(tokenDto);
        return reactiveRedisTemplate.opsForValue()
                .set(id, tokenObjectMapper, tokenParameter.getTokenExpiration())
                .doOnSuccess(aVoid -> log.info("Token save successful"))
                .doOnError(error -> log.error("Token save failed", error))
                .then();
    }

    @Override
    @SneakyThrows
    public Mono<Token> getToken(Long userId) {
        return reactiveRedisTemplate.opsForValue().get(userId)
                .handle((t, sink) -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        TokenDto tokenDto = mapper.readValue(t, TokenDto.class);
                        sink.next(tokenMapper.tokenFromTokenDto(tokenDto));
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
    }

    @Override
    public Mono<Void> deleteToken(Long userId) {
        return reactiveRedisTemplate.delete(userId)
                .doOnSuccess(l -> log.info("Token delete successful"))
                .doOnError(l -> log.warn("Token is not found"))
                .then();
    }


}
