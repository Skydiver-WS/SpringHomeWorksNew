package com.example.appnews.repository;

import com.example.appnews.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatabaseCommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentByIdAndUserId(Long id, Long userId);
}
