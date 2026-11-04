package com.newsHub.mapper;

import com.newsHub.dto.request.PermissionRequest;
import com.newsHub.dto.response.PermissionResponse;
import com.newsHub.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
