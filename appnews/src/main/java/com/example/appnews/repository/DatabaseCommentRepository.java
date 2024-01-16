package com.example.appnews.repository;

import com.example.appnews.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseCommentRepository extends JpaRepository<Comment, Long> {
}
