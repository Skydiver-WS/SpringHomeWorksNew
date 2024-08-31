package com.example.springappnewssecure.service.security;

import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<UserResponse> singIn(UserRequest userRequest);
}
