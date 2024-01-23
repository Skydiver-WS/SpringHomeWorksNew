package com.example.appnews.service;

import com.example.appnews.model.Comment;

import java.util.List;

public interface DatabaseCommentService {
    List<Comment> findAll();
    List<Comment> findCommentByUserId(Long userId);

    Comment findCommentById(Long id);
}
