package com.example.springappnewssecure.mapper;

import com.example.springappnewssecure.dto.TokenDto;
import com.example.springappnewssecure.entity.Role;
import com.example.springappnewssecure.entity.RoleType;
import com.example.springappnewssecure.entity.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    @Mapping(target = "roleList", source = "tokenDto", qualifiedByName = "addedRoleList")
    Token tokenFromTokenDto(TokenDto tokenDto);

    @Named("addedRoleList")
    default List<Role> addedRoleList(TokenDto tokenDto){
        RoleType roleType = RoleType.valueOf(tokenDto.getRole());
        return List.of(Role.from(roleType));
    }
}
