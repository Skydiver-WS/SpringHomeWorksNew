package com.example.appnews.mapper;

import com.example.appnews.model.User;
import com.example.appnews.web.request.dto.user.CreateUserRequest;
import com.example.appnews.web.response.user.ListUsersResponse;
import com.example.appnews.web.response.user.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", implementationName = "UserMapperImpl")
public interface UserMapper {
    User userToMapper(CreateUserRequest userRequest);
    UserResponse responseToMapper(User user);

    default ListUsersResponse listUsers(List<User> users){
        ListUsersResponse listUsersResponse = new ListUsersResponse();
        listUsersResponse.setUserResponseList(users.stream()
                .map(this::responseToMapper).toList());
        return listUsersResponse;
    }
}
