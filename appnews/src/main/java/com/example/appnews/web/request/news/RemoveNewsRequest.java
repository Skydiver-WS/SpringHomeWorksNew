package com.example.appnews.web.request.news;

import lombok.Data;

@Data
public class RemoveNewsRequest {
    private Long newsId;
    private Long userId;
}
