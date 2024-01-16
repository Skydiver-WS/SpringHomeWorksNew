package com.example.appnews.repository;

import com.example.appnews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseUserRepository extends JpaRepository<User, Long> {
}
