package com.example.appnews.service;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.web.request.dto.news.CreateNewsRequest;

import java.util.List;

public interface DatabaseNewsService {
    List<News> findAll();

    News findById(Long id);

    List<News> findByUser(Long userId);

    News createNews(CreateNewsRequest newsRequest);

}
