package com.example.appnews.controller;

import com.example.appnews.aop.Check;
import com.example.appnews.model.News;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.service.DatabaseUserService;
import com.example.appnews.web.response.news.ListNewsResponse;
import com.example.appnews.mapper.NewsMapper;
import com.example.appnews.web.response.news.NewsResponse;
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

    @Check
    @PostMapping("/create")
    public ResponseEntity<News> createNews(@RequestBody com.example.appnews.web.request.dto.news.CreateNewsRequest newsRequest) {
        if (userService.findById(newsRequest.getUserId()).isPresent()) {
            News news = newsService.createNews(newsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(news);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable Long id, @RequestBody com.example.appnews.web.request.dto.news.CreateNewsRequest newsRequest) {
        News news = newsMapper.newsToResponse(id, newsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
