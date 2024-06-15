package com.example.springappnewssecure.service;

import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;

import java.util.List;

public interface NewsService {

    List<NewsResponse> findAllNews();

    NewsResponse createNews(NewsRequest newsRequest);

    NewsResponse updateNews(String title, NewsRequest newsRequest);

    void removeNews(String title);

}
