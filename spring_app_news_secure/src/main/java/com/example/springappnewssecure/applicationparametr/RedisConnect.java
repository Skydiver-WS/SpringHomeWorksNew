package com.example.springappnewssecure.applicationparametr;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConnect {
    private String host;
    private Integer port;
}
