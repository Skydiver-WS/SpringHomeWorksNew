package com.example.appnews.service.impl;


import com.example.appnews.mapper.CommentMapper;
import com.example.appnews.model.Comment;
import com.example.appnews.model.News;
import com.example.appnews.repository.DatabaseCommentRepository;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.service.DatabaseCommentService;
import com.example.appnews.web.request.comment.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseCommentImpl implements DatabaseCommentService {
    private final DatabaseCommentRepository commentRepository;
    private final DatabaseNewsRepository newsRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findCommentByUserId(Long userId) {
        return null;
    }

    @Override
    public Comment findCommentById(Long id) {
        return null;
    }

    @Override
    public Comment createComment(CreateCommentRequest createCommentRequest) {
        News news = newsRepository.findNewsByTitle(createCommentRequest.getNewsTitle()).orElse(null);
        return commentRepository.save(commentMapper.commentToRequestAndRepository(createCommentRequest, news));
    }

    @Override
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }
}
