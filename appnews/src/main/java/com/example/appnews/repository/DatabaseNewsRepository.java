package com.example.appnews.repository;

import com.example.appnews.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseNewsRepository extends JpaRepository<News, Long> {
}
