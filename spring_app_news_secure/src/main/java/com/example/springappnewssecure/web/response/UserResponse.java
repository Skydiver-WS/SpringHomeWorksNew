package com.example.springappnewssecure.web.response;

import com.example.springappnewssecure.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String username;
    private String token;
    private List<Role> roles;

    private List<NewsResponse> newsResponseList;

    private String errorMessage;
}
