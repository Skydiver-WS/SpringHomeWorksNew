package com.example.springappnewssecure.web.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsRequest {

    private String author;
    private String title;
    private String content;
}
