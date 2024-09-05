package com.example.springappnewssecure.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@Data
@RedisHash("Token")
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    @Indexed
    @Id
    private Long id;
    @Indexed
    private String token;

    private List<Role> roleList;
}
