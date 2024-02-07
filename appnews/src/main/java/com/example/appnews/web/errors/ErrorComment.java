package com.example.appnews.web.errors;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ErrorComment {
    private String comment;
    private String description;
}
