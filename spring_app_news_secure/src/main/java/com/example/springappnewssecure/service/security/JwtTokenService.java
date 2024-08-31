package com.example.springappnewssecure.service.security;

import com.example.springappnewssecure.security.UserDetailsService;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtTokenService {
    String generateToken(UserDetailsService userDetailsService);
    Claims decoderToken(String token);
    String [] injectUserNameAndPasswordFromToken(String token);
    boolean isValidToken(String token);

}
