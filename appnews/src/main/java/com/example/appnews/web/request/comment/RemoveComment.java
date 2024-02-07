package com.example.appnews.web.request.comment;

import lombok.Data;

@Data
public class RemoveComment {
    private Long id;
    private Long userId;
    private Long newsId;
}
