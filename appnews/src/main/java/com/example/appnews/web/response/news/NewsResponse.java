package com.example.appnews.web.response.news;


import lombok.Data;


@Data

public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private int commentSize;
    private String authorNikName;
}
