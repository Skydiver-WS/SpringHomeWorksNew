package com.example.appnews.web.request.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {
    private String title;
    private String description;
    private Long userId;
}
