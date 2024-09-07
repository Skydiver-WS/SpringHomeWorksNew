package com.example.springappnewssecure.repository;

import com.example.springappnewssecure.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    void deleteByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndUsername(Long id, String username);
}
