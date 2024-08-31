package com.example.springappnewssecure.entity;

import jakarta.persistence.Index;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RedisHash("Token")
@Builder
public class Token implements Serializable {
    @Indexed
    private Long id;
    @Indexed
    private String token;
}
