package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NewsMapper.class})
public interface UserMapper {

    UserResponse userResponseFromUser(User user);

    User userFromUserRequest(UserRequest userRequest);

    List<UserResponse> listUserResponseFromListUsers(List<User> userList);
    void updateUserFromUserRequest(@MappingTarget User user, UserRequest userRequest);

}
