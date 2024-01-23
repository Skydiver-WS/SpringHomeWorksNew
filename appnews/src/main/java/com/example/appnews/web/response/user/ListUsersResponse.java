package com.example.appnews.web.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersResponse {
    private List<UserResponse> userResponseList = new ArrayList<>();
}
