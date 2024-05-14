package com.example.todoappspringreactive.mapper;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.TaskResponse;
import com.example.todoappspringreactive.web.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "author", source = "user")
    @Mapping(target = "id", source = "task.id")
    TaskResponse taskToTaskResponse(Task task, User user);
    @Mapping(target = "createAt", expression = "java(java.time.Instant.now())")
    void taskToTaskRequest(@MappingTarget Task task, String authorId);
}
