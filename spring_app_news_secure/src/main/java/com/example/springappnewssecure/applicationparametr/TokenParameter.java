package com.example.springappnewssecure.applicationparametr;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "app.jwt")
@Data
public class TokenParameter {
    private String secret;
    private Duration tokenExpiration;
    private String refreshTokenExpiration;
}
