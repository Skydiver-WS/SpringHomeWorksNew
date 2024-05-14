package com.example.todoappspringreactive.web.response;

import lombok.*;

@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
}
