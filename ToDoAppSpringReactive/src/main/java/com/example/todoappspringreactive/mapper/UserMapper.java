package com.example.todoappspringreactive.mapper;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "taskResponse.author", ignore = true)
    @Mapping(target = "taskResponse.observers", ignore = true)
    @Mapping(target = "id", source = "user.id")
    UserResponse taskToUserResponse(User user, Task task);

}
