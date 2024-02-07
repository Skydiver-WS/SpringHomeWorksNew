package com.example.appnews.service.impl;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.repository.DatabaseUserRepository;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.mapper.NewsMapper;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.EditNewsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public News createNews(CreateNewsRequest newsRequest) {
        User user = userRepository.findById(newsRequest.getUserId()).orElse(null);
        return newsRepository.save(newsMapper.newsToResponse(user, newsRequest));
    }

    @Override
    public void deleteNewsByTitle(EditNewsRequest editNewsRequest) {
        newsRepository.deleteNewsByTitle(editNewsRequest.getTitle());
    }

    @Override
    public News updateNews(EditNewsRequest editNewsRequest) {
        Optional<News> news = newsRepository.findNewsByTitle(editNewsRequest.getTitle());
        if (news.isPresent()){
            News news1 = news.get();
            news1.setDescription(editNewsRequest.getDescription());
            return newsRepository.save(news1);
        }
        return null;
    }
}
