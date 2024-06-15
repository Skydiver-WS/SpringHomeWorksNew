package com.example.springappnewssecure.web.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    private String username;
    private String password;
}
