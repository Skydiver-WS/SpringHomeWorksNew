package com.example.springappnewssecure.web.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsResponse {

    private String title;
    private String content;
    private String author;
    private String errorMessage;
}
