package com.example.todoappspringreactive.service;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.web.response.TaskResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Flux<TaskResponse> findAll();

    Mono<Task> createTask(Task task);

    Mono<Task> updateTask(Task task, String id);

    Mono<Task> addObserv(Task task);

    Mono<Task> deleteTask(String id);
}
