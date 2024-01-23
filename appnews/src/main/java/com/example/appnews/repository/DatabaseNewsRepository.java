package com.example.appnews.repository;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatabaseNewsRepository extends JpaRepository<News, Long> {
    List<News> findNewsByUserId(Long userId);
}
