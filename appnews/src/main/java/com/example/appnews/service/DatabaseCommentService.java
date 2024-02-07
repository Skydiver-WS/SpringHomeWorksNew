package com.example.appnews.service;

import com.example.appnews.model.Comment;
import com.example.appnews.web.request.comment.CreateCommentRequest;

import java.util.List;

public interface DatabaseCommentService {
    List<Comment> findAll();
    List<Comment> findCommentByUserId(Long userId);

    Comment findCommentById(Long id);
    Comment createComment(CreateCommentRequest createCommentRequest);

    void removeComment(Long id);
}
