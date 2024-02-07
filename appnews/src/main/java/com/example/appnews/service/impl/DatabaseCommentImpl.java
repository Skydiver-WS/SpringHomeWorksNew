package com.example.appnews.service.impl;


import com.example.appnews.mapper.CommentMapper;
import com.example.appnews.model.Comment;
import com.example.appnews.model.News;
import com.example.appnews.repository.DatabaseCommentRepository;
import com.example.appnews.repository.DatabaseNewsRepository;
import com.example.appnews.service.DatabaseCommentService;
import com.example.appnews.web.request.comment.CreateCommentRequest;
import com.example.appnews.web.request.comment.EditComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Comment editComment(EditComment editComment) {
        Comment newComment = commentRepository.findById(editComment.getId()).orElse(null);
        if (newComment != null) {
            newComment.setComment(editComment.getNewComment());
            return commentRepository.save(newComment);
        }
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
