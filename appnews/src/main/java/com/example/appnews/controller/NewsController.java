package com.example.appnews.controller;

import com.example.appnews.aop.Check;
import com.example.appnews.model.News;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.service.DatabaseUserService;
import com.example.appnews.web.request.news.CreateNewsRequest;
import com.example.appnews.web.request.news.RemoveNewsRequest;
import com.example.appnews.web.request.news.UpdateNewsRequest;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.mapper.NewsMapper;
import com.example.appnews.web.response.news.NewsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsMapper newsMapper;
    private final DatabaseNewsService newsService;


    @GetMapping
    public ResponseEntity<ListNewsResponse> findAllNews() {
        return ResponseEntity.ok(newsMapper.newsListResponse(newsService.findAll()));
    }

    @PostMapping("/create")
    @Check
    public ResponseEntity<News> createNews(@RequestBody @Valid CreateNewsRequest newsRequest) {
        News news = newsService.createNews(newsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }

    @Check
    @DeleteMapping("/remove")
    public ResponseEntity<Void> deleteNews(@RequestBody RemoveNewsRequest removeNewsRequest) {
        newsService.deleteNewsById(removeNewsRequest.getNewsId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Check
    @PutMapping("/update/{id}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable Long id, @RequestBody UpdateNewsRequest newsRequest) {
        News news = newsMapper.newsToResponse(id, newsRequest);
        NewsResponse newsResponse = newsMapper.newsToResponse(newsService.updateNews(news));
        return ResponseEntity.status(HttpStatus.OK).body(newsResponse);
    }


}
