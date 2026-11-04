package com.newsHub.service;

import com.newsHub.Repository.PermissionRepository;
import com.newsHub.Repository.RoleRepository;
import com.newsHub.dto.request.RoleRequest;
import com.newsHub.dto.response.RoleResponse;
import com.newsHub.entity.Permission;
import com.newsHub.entity.Role;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.RoleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Transactional
    public RoleResponse roleRequest(RoleRequest request){
        Role role = roleMapper.toRole(request);

        Set<Permission> permissions = request.getPermissions().stream().map(permissionId
        -> permissionRepository.findById(Integer.valueOf(permissionId)).orElseThrow(
                ()->     new AppException(ErrorCode.PERMISSION_NOT_EXISTED)
        )).collect(Collectors.toSet());

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }
}
