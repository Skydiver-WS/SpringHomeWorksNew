package com.example.springappnewssecure.web.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private String username;
    private String password;

    private List<NewsResponse> newsResponseList;

    private String errorMessage;
}
