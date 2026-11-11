package com.newsHub.mapper;

import com.newsHub.dto.request.UpdatePassword;
import com.newsHub.dto.request.UpdateRoleUserRequest;
import com.newsHub.dto.request.UserRequest;
import com.newsHub.dto.request.UserUpdateRequest;
import com.newsHub.dto.response.UserResponse;
import com.newsHub.entity.Role;
import com.newsHub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles",ignore = true)
    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
    void toUpdate(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
    void toUpdateUserRole(@MappingTarget User user, UpdateRoleUserRequest request);

    default String map(Role role){
        return role == null ? null : role.getName();
    }
}
