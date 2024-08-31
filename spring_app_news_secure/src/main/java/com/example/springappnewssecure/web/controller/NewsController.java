package com.example.springappnewssecure.web.controller;

import com.example.springappnewssecure.service.NewsService;
import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public Flux<List<NewsResponse>> finaAllNews(){
        return newsService.findAllNews();
    }

    @PostMapping
    public Mono<NewsResponse> createNews(@RequestBody NewsRequest newsRequest){
        return newsService.createNews(newsRequest);
    }

    @PutMapping
    public Mono<NewsResponse> updateNews(@RequestParam String title, @RequestBody NewsRequest newsRequest){
        return newsService.updateNews(title, newsRequest);
    }

    @DeleteMapping
    public void deleteNews(@RequestParam String title){
        newsService.removeNews(title);
    }
}
