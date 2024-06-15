package com.example.springappnewssecure.service.impl;

import com.example.springappnewssecure.entity.News;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.mapper.NewsMapper;
import com.example.springappnewssecure.repository.NewsRepository;
import com.example.springappnewssecure.repository.UserRepository;
import com.example.springappnewssecure.service.NewsService;
import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsMapper newsMapper;
    @Override
    public List<NewsResponse> findAllNews() {
        log.info("Find all news.");
        return newsMapper.listNewsResponseFromListNews(newsRepository.findAll());
    }

    @Override
    public NewsResponse createNews(NewsRequest newsRequest) {
        log.info("Create news: {}", newsRequest);
        User user = userRepository.findByUsername(newsRequest.getAuthor())
                .orElseThrow(() -> new RuntimeException("User " + newsRequest.getAuthor() + " not found"));
        News news = newsRepository.save(newsMapper.newsFromNewsRequest(newsRequest, user));
        log.info("News {} created.", news.getTitle());
        return newsMapper.newsResponseFromNews(news);
    }

    @Override
    public NewsResponse updateNews(String title, NewsRequest newsRequest) {
        log.info("Update news {} ", title);
        News news = newsRepository.findByTitle(title).orElseThrow(() ->
                new RuntimeException("News " + title + " not found"));
        newsMapper.updateNewsFromNewsResponse(news, newsRequest);
        try {
            News newsUpdate = newsRepository.save(news);
            log.info("News {} update successful.", newsUpdate);
            return newsMapper.newsResponseFromNews(newsUpdate);
        } catch (Exception ex){
            log.error("Title {} exists", newsRequest.getTitle());
            return NewsResponse.builder()
                    .title(newsRequest.getTitle())
                    .errorMessage(ex.getMessage())
                    .build();
        }
    }

    @Override
    public void removeNews(String title) {
        log.info("News remove by title {}", title);
        newsRepository.deleteByTitle(title);
        log.info("News removed by title {} successful", title);
    }
}
