package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.User;
import com.example.springappnewssecure.web.request.UserRequest;
import com.example.springappnewssecure.web.response.UserResponse;
import org.mapstruct.*;
import org.mapstruct.ap.internal.util.Strings;
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

    @Mapping(target = "password", source = "userRequest.password", qualifiedByName = "setPassword",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "username", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserRequest(@MappingTarget User user, UserRequest userRequest);

    @Named("setPassword")
    default String setPassword(String password){
        if(Strings.isNotEmpty(password) ||
        !password.isBlank()){
         return new BCryptPasswordEncoder()
                 .encode(password);
        }
        return null;
    }

}
