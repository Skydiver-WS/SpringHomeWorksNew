package com.example.appnews.web.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCreateComment {
    private String comment;
    private String description;
}
