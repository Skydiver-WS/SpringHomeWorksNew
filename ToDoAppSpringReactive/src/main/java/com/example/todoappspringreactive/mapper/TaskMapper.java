package com.example.todoappspringreactive.mapper;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.web.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse taskToTaskResponse(Task task);
}
