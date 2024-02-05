package com.example.appnews.web.request.news;

import lombok.Data;

@Data
public class UpdateNewsRequest {
    private String nikName;
    private String title;
    private String description;
}
