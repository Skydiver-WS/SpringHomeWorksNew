package com.example.todoappspringreactive.service;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.web.request.ObserversRequest;
import com.example.todoappspringreactive.web.response.TaskResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Flux<TaskResponse> findAll();

    Mono<TaskResponse> findById(String id);

    Mono<Task> createTask(Task task, String authorId);

    Mono<Task> updateTask(Task task, String id);

    Mono<Task> addObserv(ObserversRequest observersRequest, String id);

    Mono<Void> deleteTask(String id);
}
