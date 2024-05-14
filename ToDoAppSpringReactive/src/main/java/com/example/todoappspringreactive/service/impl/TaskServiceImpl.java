package com.example.todoappspringreactive.service.impl;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.mapper.TaskMapper;
import com.example.todoappspringreactive.repository.TaskRepository;
import com.example.todoappspringreactive.repository.UserRepository;
import com.example.todoappspringreactive.service.TaskService;
import com.example.todoappspringreactive.web.request.ObserversRequest;
import com.example.todoappspringreactive.web.response.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public Flux<TaskResponse> findAll() {
        return taskRepository.findAll()
                .flatMap(this::createTaskResponse);
    }


    @Override
    public Mono<TaskResponse> findById(String id) {
        return taskRepository.findById(id)
                .flatMap(this::createTaskResponse);
    }


    @Override
    public Mono<Task> createTask(Task task, String authorId) {
        taskMapper.taskToTaskRequest(task, authorId);
        return taskRepository.save(task)
                .flatMap(user -> Mono.zip(userRepository.findById(authorId),
                        Mono.just(task)))
                .map(Tuple2::getT2);
    }

    @Override
    public Mono<Task> updateTask(Task task, String id) {
        return taskRepository.findByIdAndAuthorId(id, task.getAuthorId())
                .flatMap(t -> {
                    if (Objects.nonNull(task.getDescription())) {
                        t.setDescription(task.getDescription());
                        log.info("Description updated");
                    }
                    if (Objects.nonNull(task.getName())) {
                        t.setName(task.getName());
                        log.info("Name updated");
                    }
                    if (Objects.nonNull(task.getStatus())) {
                        t.setStatus(task.getStatus());
                        log.info("Status updated");
                    }
                    t.setUpdateAt(Instant.now());
                    log.info("Task updated");
                    return taskRepository.save(t);
                });
    }

    @Override
    public Mono<Task> addObserv(ObserversRequest observersRequest, String id) {
        Mono<Task> taskMono = taskRepository.findById(id);
        Flux<User> userFlux = userRepository.findUsersByIdIn(observersRequest.getAuthorId());
        return Mono.zip(taskMono, userFlux.collectList())
                .flatMap(tuple -> {
                    Task task = tuple.getT1();
                    List<User> users = tuple.getT2();
                    task.setObserverIds(users.stream().map(User::getId).collect(Collectors.toSet()));
                    return taskRepository.save(task);
                });
    }


    @Override
    public Mono<Void> deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

    private Mono<TaskResponse> createTaskResponse(Task task){
        Mono<User> authorMono = userRepository.findById(task.getAuthorId());
        Flux<User> observersFlux = userRepository.findUsersByIdIn(task.getObserverIds());
        return Mono.zip(authorMono, observersFlux.collectList())
                .map(tuple -> {
                    TaskResponse taskResponse = taskMapper.taskToTaskResponse(task, tuple.getT1());
                    taskResponse.setObservers(new HashSet<>(tuple.getT2()));
                    return taskResponse;
                });
    }
}
