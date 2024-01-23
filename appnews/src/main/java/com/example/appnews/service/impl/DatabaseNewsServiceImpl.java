package com.example.appnews.service.impl;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.web.request.dto.news.CreateNewsRequest;
import com.example.appnews.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DatabaseNewsServiceImpl implements DatabaseNewsService {
    private final DatabaseNewsRepository newsRepository;
    private final DatabaseUserRepository userRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return null;
    }

    @Override
    public List<News> findByUser(Long userId) {
        return newsRepository.findNewsByUserId(userId);
    }

    @Override
    public News createNews(CreateNewsRequest newsRequest) {
        User user = userRepository.findById(newsRequest.getUserId()).orElse(null);
        return newsRepository.save(newsMapper.newsToResponse(user, newsRequest));
    }
}
