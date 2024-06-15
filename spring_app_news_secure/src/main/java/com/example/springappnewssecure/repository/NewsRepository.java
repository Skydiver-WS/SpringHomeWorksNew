package com.example.springappnewssecure.repository;

import com.example.springappnewssecure.entity.News;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByTitle(String title);

    @Transactional
    void deleteByTitle(String title);
}
