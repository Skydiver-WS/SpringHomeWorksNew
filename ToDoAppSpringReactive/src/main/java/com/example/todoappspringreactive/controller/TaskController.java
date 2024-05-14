package com.example.todoappspringreactive.controller;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.service.TaskService;
import com.example.todoappspringreactive.web.request.ObserversRequest;
import com.example.todoappspringreactive.web.response.TaskResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public Flux<TaskResponse> getAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/get-task-by-id")
    public Mono<ResponseEntity<TaskResponse>> getTaskById(@RequestParam String id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Task>> createTask(@RequestBody Task task, @RequestParam String authorId) {
        return taskService.createTask(task, authorId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<Task>> updateTask(@RequestBody @Valid Task task, @RequestParam String id) {
        return taskService.updateTask(task, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/add-observ")
    public Mono<ResponseEntity<Task>> addObservTask(@RequestBody @Valid ObserversRequest request, @RequestParam String id) {
        return taskService.addObserv(request, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteTask(@RequestParam String id) {
        return taskService.deleteTask(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
