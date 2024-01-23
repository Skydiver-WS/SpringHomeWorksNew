package com.example.appnews.service.impl;


import com.example.appnews.model.Comment;
import com.example.appnews.service.DatabaseCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseCommentImpl implements DatabaseCommentService {

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findCommentByUserId(Long userId) {
        return null;
    }

    @Override
    public Comment findCommentById(Long id) {
        return null;
    }
}
