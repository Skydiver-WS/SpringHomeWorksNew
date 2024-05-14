package com.example.todoappspringreactive.mapper;

import com.example.todoappspringreactive.entity.Task;
import com.example.todoappspringreactive.entity.User;
import com.example.todoappspringreactive.web.response.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-14T23:41:43+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse taskToUserResponse(User user, Task task) {
        if ( user == null && task == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        if ( user != null ) {
            userResponse.id( user.getId() );
            userResponse.username( user.getUsername() );
            userResponse.email( user.getEmail() );
        }

        return userResponse.build();
    }
}
