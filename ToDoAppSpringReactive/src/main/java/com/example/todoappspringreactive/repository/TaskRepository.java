package com.example.todoappspringreactive.repository;

import com.example.todoappspringreactive.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
    Mono<Task> findByIdAndAuthorId (String id, String authorId);
    @Transactional
    Mono<Void> deleteByAuthorId(String authorId);
}
