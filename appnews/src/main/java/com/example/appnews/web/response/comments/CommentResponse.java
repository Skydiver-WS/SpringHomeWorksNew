package com.example.appnews.web.response.comments;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String comment;
    private String nikName;
    private Long newsId;
    private String title;
}
