package com.example.appnews.controller;

import com.example.appnews.model.News;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.service.DatabaseUserService;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.mapper.NewsMapper;
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
    private final DatabaseUserService userService;

    @GetMapping
    public ResponseEntity<ListNewsResponse> findAllNews() {
        return ResponseEntity.ok(newsMapper.newsListResponse(newsService.findAll()));
    }

    @PostMapping("/create")
    public ResponseEntity<News> createNews(@RequestBody com.example.appnews.web.request.dto.news.CreateNewsRequest newsRequest){
        if(userService.findById(newsRequest.getUserId()).isPresent()){
            News news = newsService.createNews(newsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(news);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


}
