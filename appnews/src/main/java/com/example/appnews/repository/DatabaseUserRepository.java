package com.example.appnews.repository;

import com.example.appnews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatabaseUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNikName(String nikName);
    User findNikNameById(Long id);
}
