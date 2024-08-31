package com.example.springappnewssecure.dto;


import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationResult;

@Getter
public class ResponseErrorException extends AccessDeniedException {
    private final String username;


    public ResponseErrorException(String msg, String username) {
        super(msg);
        this.username = username;
    }
}
