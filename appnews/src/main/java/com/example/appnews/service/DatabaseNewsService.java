package com.example.appnews.service;

import com.example.appnews.model.News;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.EditNewsRequest;


import java.util.List;

public interface DatabaseNewsService {
    List<News> findAll();

    News createNews(CreateNewsRequest newsRequest);

    void deleteNewsByTitle(EditNewsRequest editNewsRequest);

    News updateNews(EditNewsRequest editNewsRequest);

}
