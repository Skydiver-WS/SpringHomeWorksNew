package com.example.appnews.controller;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.web.request.dto.news.CreateNewsRequest;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.web.response.news.mapper.NewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsMapper newsMapper;
    private final DatabaseNewsService newsService;

    @Autowired
    public NewsController(NewsMapper newsMapper, DatabaseNewsService newsService) {
        this.newsMapper = newsMapper;
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<ListNewsResponse> findAllNews() {
        return ResponseEntity.ok(newsMapper.newsListResponse(newsService.findAll()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNews(@RequestBody CreateNewsRequest newsRequest){
        News news = newsService.createNews(newsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(news);
    }
//
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<?> updateNews(@PathVariable Long id){
//        News newsUpdate = newsService.update();
//        return ResponseEntity.ok();
//    }

}
