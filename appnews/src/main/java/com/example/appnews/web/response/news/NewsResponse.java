package com.example.appnews.web.response.news;


import com.example.appnews.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private int commentSize;
    private User user;
}
