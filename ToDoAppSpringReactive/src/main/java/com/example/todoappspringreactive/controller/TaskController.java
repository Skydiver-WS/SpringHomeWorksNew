package com.example.todoappspringreactive.controller;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.service.TaskService;
import com.example.todoappspringreactive.web.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public Flux<TaskResponse> getAllTasks() {
        return taskService.findAll();
    }

    @PostMapping
    public Mono<Task> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update")
    public Mono<Task> updateTask(@RequestBody Task task, @RequestParam String id) {
       return taskService.updateTask(task, id);
    }

    @PutMapping("/add-observ")
    public Mono<Task> addObservTask(@RequestBody Task task) {
        return taskService.addObserv(task);
    }

    @DeleteMapping
    public Mono<Task> deleteTask(@RequestParam String id) {
        return taskService.deleteTask(id);
    }
}
