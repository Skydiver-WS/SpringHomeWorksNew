package com.example.todoappspringreactive.service.impl;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.mapper.TaskMapper;
import com.example.todoappspringreactive.repository.TaskRepository;
import com.example.todoappspringreactive.repository.UserRepository;
import com.example.todoappspringreactive.service.TaskService;
import com.example.todoappspringreactive.service.UserService;
import com.example.todoappspringreactive.web.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    public Flux<TaskResponse> findAll() {
        return taskRepository.findAll().
                flatMap(task -> {
                    Mono<User> userMono = userService.findById(task.getAuthorId());
                    return Mono.zip(Mono.just(task), userMono)
                            .map(task1 -> taskMapper.taskToTaskResponse(task));
                });
    }

    @Override
    public Mono<Task> createTask(Task task) {
        return null;
    }

    @Override
    public Mono<Task> updateTask(Task task, String id) {
        return null;
    }

    @Override
    public Mono<Task> addObserv(Task task) {
        return null;
    }

    @Override
    public Mono<Task> deleteTask(String id) {
        return null;
    }
}
