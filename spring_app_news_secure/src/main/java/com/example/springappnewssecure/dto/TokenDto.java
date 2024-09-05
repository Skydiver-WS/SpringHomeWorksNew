package com.example.springappnewssecure.dto;

import com.example.springappnewssecure.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private Long id;

    private String token;

    private String role;
}
