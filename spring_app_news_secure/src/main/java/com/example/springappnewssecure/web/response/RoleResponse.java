package com.example.springappnewssecure.web.response;

import com.example.springappnewssecure.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RoleResponse {
    private String role;
}
