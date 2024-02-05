package com.example.appnews.web.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCreateNews {
    private Long userId;
    private String message;
}
