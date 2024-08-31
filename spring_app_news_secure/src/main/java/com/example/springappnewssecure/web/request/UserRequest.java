package com.example.springappnewssecure.web.request;

import com.example.springappnewssecure.entity.RoleType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    private Long id;
    private String username;
    private String password;
}
