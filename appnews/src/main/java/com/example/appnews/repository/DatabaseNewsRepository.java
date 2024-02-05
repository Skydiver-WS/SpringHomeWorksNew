package com.example.appnews.repository;

import com.example.appnews.model.News;
import com.example.appnews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatabaseNewsRepository extends JpaRepository<News, Long> {
    List<News> findNewsByUserId(Long userId);
    News findNewsByIdAndUserId(Long id, Long userId);
    News findNewsByTitleAndUserNikName(String title, String nikName);
    Optional<News> findNewsByTitle(String title);
}
