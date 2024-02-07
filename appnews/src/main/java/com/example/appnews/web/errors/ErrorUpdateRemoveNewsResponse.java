package com.example.appnews.web.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorUpdateRemoveNewsResponse {
    private String title;
    private String nikName;
    private String messages;
}
