package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.security.UserDetailsService;
import com.example.springappnewssecure.service.impl.UserServiceImpl;
import com.example.springappnewssecure.service.security.impl.JwtTokenServiceImpl;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {NewsMapper.class},
        imports = {
                BCryptPasswordEncoder.class
        })
public interface UserMapper {


    UserResponse userResponseFromUser(User user);

    @Mapping(target = "password", expression = "java(new BCryptPasswordEncoder()" +
            ".encode(userRequest.getPassword()))")
    @Mapping(target = "roles", expression = "java(roles.stream().peek(r -> r.setUser(user)).toList())")
    User userFromUserRequest(UserRequest userRequest, List<Role> roles);

    List<UserResponse> listUserResponseFromListUsers(List<User> userList);

    @Mapping(target = "password", expression = "java(new BCryptPasswordEncoder()" +
            ".encode(userRequest.getPassword()))")
    @Mapping(target = "username", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserRequest(@MappingTarget User user, UserRequest userRequest);

}
