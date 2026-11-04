package com.newsHub.mapper;

import com.newsHub.dto.request.RoleRequest;
import com.newsHub.dto.response.RoleResponse;
import com.newsHub.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
