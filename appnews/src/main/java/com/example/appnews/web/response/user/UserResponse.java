package com.example.appnews.web.response.user;

import com.example.appnews.model.Comment;
import com.example.appnews.model.News;
import com.example.appnews.web.response.comments.CommentResponse;
import com.example.appnews.web.response.news.NewsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String nikName;
    private List<NewsResponse> newsList;
    private List<CommentResponse> commentList;
}
