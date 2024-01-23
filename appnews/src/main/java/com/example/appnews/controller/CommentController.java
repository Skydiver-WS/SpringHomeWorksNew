package com.example.appnews.controller;


import com.example.appnews.service.DatabaseCommentService;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.web.request.comment.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final DatabaseCommentService commentService;
    private final DatabaseNewsService newsService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(null);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
