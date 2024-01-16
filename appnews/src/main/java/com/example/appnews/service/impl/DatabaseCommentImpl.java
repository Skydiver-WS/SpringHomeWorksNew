package com.example.appnews.service.impl;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.service.DatabaseNewsService;

import com.example.appnews.web.request.dto.news.CreateNewsRequest;
import com.example.appnews.web.response.news.mapper.NewsMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentImpl implements DatabaseNewsService {
    private final DatabaseNewsRepository newsRepository;
    private final DatabaseUserRepository userRepository;
    private final NewsMapper newsMapper;

//    @Autowired
//    public DatabaseCommentImpl(DatabaseNewsRepository newsRepository) {
//        this.newsRepository = newsRepository;
//    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return null;
    }

    @Override
    public List<News> findByUser(User user) {
        return null;
    }

    @Override
    public News createNews(CreateNewsRequest newsRequest) {
        User user = userRepository.findById(newsRequest.getUserId()).orElse(null);
        return newsRepository.save(newsMapper.newsToResponse(user, newsRequest));
    }
}
