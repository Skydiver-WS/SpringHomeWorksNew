package com.example.springappnewssecure.web.controller;

import com.example.springappnewssecure.aop.LoggingController;
import com.example.springappnewssecure.service.NewsService;
import com.example.springappnewssecure.web.request.NewsRequest;
import com.example.springappnewssecure.web.response.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @LoggingController
    public Flux<List<NewsResponse>> finaAllNews(ServerHttpRequest serverHttpRequest){
        return newsService.findAllNews();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @PostMapping
    @LoggingController
    public Mono<NewsResponse> createNews(ServerHttpRequest serverHttpRequest, @RequestParam Long id, @RequestBody NewsRequest newsRequest){
        return newsService.createNews(id, newsRequest);
    }
    @PreAuthorize("hasAnyRole('ROLE_MODERATOR')")
    @PutMapping
    @LoggingController
    public Mono<NewsResponse> updateNews(ServerHttpRequest serverHttpRequest, @RequestParam Long id, @RequestParam String title,
                                         @RequestBody NewsRequest newsRequest){
        return newsService.updateNews(title, newsRequest);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @DeleteMapping
    @LoggingController
    public Mono<Void> deleteNews(ServerHttpRequest serverHttpRequest, @RequestParam Long id,@RequestParam String title){

        return newsService.removeNews(title);
    }
}
