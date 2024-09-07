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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsMapper newsMapper;

    @Override
    public Flux<List<NewsResponse>> findAllNews() {
        log.info("Find all news.");
        return Flux.just(newsMapper.listNewsResponseFromListNews(newsRepository.findAll()));
    }

    @Override
    public Mono<NewsResponse> createNews(Long userId,NewsRequest newsRequest) {
        log.info("Create news: {}", newsRequest);
        return Mono.fromCallable(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User " + userId + " not found"));
                    News news = newsRepository.save(newsMapper.newsFromNewsRequest(newsRequest, user));
                    log.info("News {} created.", news.getTitle());
                    return news;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(newsMapper::newsResponseFromNews);

    }

    @Override
    public Mono<NewsResponse> updateNews(String title, NewsRequest newsRequest) {
        return Mono.fromCallable(() -> {
                    log.info("Update news {} ", title);
                    News news = newsRepository.findByTitle(title).orElseThrow(() ->
                            new RuntimeException("News " + title + " not found"));
                    newsMapper.updateNewsFromNewsResponse(news, newsRequest);
                    News newsUpdate = newsRepository.save(news);
                    log.info("News {} update successful.", newsUpdate);
                    return news;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map(newsMapper::newsResponseFromNews)
                .onErrorResume(e -> {
                    log.error("Title {} exists", newsRequest.getTitle());
                    return Mono.just(NewsResponse.builder()
                            .title(newsRequest.getTitle())
                            .errorMessage(e.getMessage())
                            .build());
                });
    }

    @Override
    public void removeNews(String title) {
        log.info("News remove by title {}", title);
        newsRepository.deleteByTitle(title);
        log.info("News removed by title {} successful", title);
    }
}
