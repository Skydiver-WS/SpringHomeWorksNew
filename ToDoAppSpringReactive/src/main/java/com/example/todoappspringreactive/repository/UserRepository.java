package com.example.todoappspringreactive.repository;

import com.example.todoappspringreactive.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.util.Set;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findUsersByIdIn(Set<String> ids);
}
