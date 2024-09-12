package com.example.springappnewssecure.service;

import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface NewsService {

    Flux<List<NewsResponse>> findAllNews();

    Mono<NewsResponse> createNews(Long userId, NewsRequest newsRequest);

    Mono<NewsResponse> updateNews(String title, NewsRequest newsRequest);

    Mono<Void> removeNews(String title);

}
