package com.newsHub.service;

import com.newsHub.Repository.PermissionRepository;
import com.newsHub.Repository.RoleRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.RoleRequest;
import com.newsHub.dto.request.UpdateRoleRequest;
import com.newsHub.dto.response.RoleResponse;
import com.newsHub.entity.Permission;
import com.newsHub.entity.Role;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.RoleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse roleRequest(RoleRequest request){
        Role role = roleMapper.toRole(request);

        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }

        Set<Permission> permissions = request.getPermissions()
                .stream()
                .map(
                        permissionName -> permissionRepository.findByName(permissionName)
                                .orElseThrow(
                                        ()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND)
                                )
        ).collect(Collectors.toSet());

        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<RoleResponse> getRoles(){
        return roleRepository.findAllWithPermissions().stream()
                .map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public RoleResponse getRole(Integer roleId){
        var role = roleRepository.findByIdWithPermissions(roleId).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );

        return roleMapper.toRoleResponse(role);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public RoleResponse updateRole(UpdateRoleRequest request,Integer roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );

        if(Set.of("ADMIN", "EDITOR", "VIEWER").contains(role.getName())
                && !role.getName().equals(request.getName())){
            throw new AppException(ErrorCode.ROLE_CANNOT_RENAME);
        }

        if(roleRepository.existsByNameAndRoleIdNot(request.getName(),roleId)){
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }



        Set<Permission> permissions = request.getPermissions().stream().map(
                permissionName -> permissionRepository.findByName(permissionName)
                        .orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND))
        ).collect(Collectors.toSet());

        role.setPermissions(permissions);

        roleMapper.toUpdate(role,request);

        return roleMapper.toRoleResponse(role);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Integer roleId){
        var role = roleRepository.findById(roleId).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );

        if (Set.of("ADMIN", "EDITOR", "VIEWER")
                .contains(role.getName())) {
            throw new AppException(ErrorCode.ROLE_CANNOT_DELETE);
        }

        if(userRepository.existsByRolesContaining(role)){
            throw new AppException(ErrorCode.ROLE_IN_USE);
        }

        roleRepository.delete(role);
    }





}
