package com.example.appnews.controller;


import com.example.appnews.aop.Check;
import com.example.appnews.mapper.CommentMapper;
import com.example.appnews.service.DatabaseCommentService;
import com.example.appnews.service.DatabaseNewsService;
import com.example.appnews.web.request.comment.CreateCommentRequest;
import com.example.appnews.web.request.comment.RemoveComment;
import com.example.appnews.web.response.comments.CommentResponse;
import com.example.appnews.web.response.comments.ListCommentsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final DatabaseCommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<ListCommentsResponse> findAll() {
        return ResponseEntity.ok(commentMapper.findAll(commentService.findAll()));
    }

    @Check
    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        CommentResponse commentResponse = commentMapper.commentToResponse(commentService.createComment(createCommentRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentResponse);
    }

    @Check
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeComment(@RequestBody RemoveComment removeComment) {
        commentService.removeComment(removeComment.getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
