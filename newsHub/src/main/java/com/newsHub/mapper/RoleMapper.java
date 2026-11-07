package com.newsHub.mapper;

import com.newsHub.dto.request.RoleRequest;
import com.newsHub.dto.request.UpdateRoleRequest;
import com.newsHub.dto.response.RoleResponse;
import com.newsHub.entity.Permission;
import com.newsHub.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest roleRequest);


    RoleResponse toRoleResponse(Role role);
    void toUpdate(@MappingTarget Role role, UpdateRoleRequest roleRequest);

    default Set<String> mapPermissions(Set<Permission> permissions) {
        if (permissions == null) {
            return Set.of();
        }

        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }


}
