package com.newsHub.service;

import com.newsHub.Repository.PermissionRepository;
import com.newsHub.Repository.RoleRepository;
import com.newsHub.dto.request.PermissionRequest;
import com.newsHub.dto.request.UpdatePermissionRequest;
import com.newsHub.dto.response.PermissionResponse;
import com.newsHub.entity.Permission;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.PermissionMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PermissionResponse requestPermission(PermissionRequest request){
        if(permissionRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getPermissions(){
        var permission = permissionRepository.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse getPermission(Integer permissionId){
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(
                        ()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND)
                );

        return permissionMapper.toPermissionResponse(permission);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Integer permissionId){
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                ()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND)
        );

        if(roleRepository.existsByPermissions_Id(permissionId)){
            throw new AppException(ErrorCode.PERMISSION_IN_USE);
        }

        permissionRepository.delete(permission);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public PermissionResponse updatePermission(UpdatePermissionRequest request,Integer permissionId){


        var permission = permissionRepository.findById(permissionId).orElseThrow(
                ()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND)
        );

        if(request.getName() != null && !request.getName().equals(permission.getName())){
            if(permissionRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.PERMISSION_EXISTED);
            }

            if(roleRepository.existsByPermissions_Id(permissionId)){
                throw new AppException(ErrorCode.PERMISSION_IN_USE);
            }

        }

        permissionMapper.toUpdatePermission(permission,request);

        return permissionMapper.toPermissionResponse(permission);
    }

}
