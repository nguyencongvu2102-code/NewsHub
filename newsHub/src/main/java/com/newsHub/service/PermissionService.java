package com.newsHub.service;

import com.newsHub.Repository.PermissionRepository;
import com.newsHub.dto.request.PermissionRequest;
import com.newsHub.dto.response.PermissionResponse;
import com.newsHub.entity.Permission;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.PermissionMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE,makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Transactional
    public PermissionResponse requestPermission(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getPermissions(){
        var permission = permissionRepository.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }


    @Transactional
    public void delete(Integer permissionId){
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                ()-> new AppException(ErrorCode.PERMISSION_NOT_EXISTED)
        );

        permissionRepository.delete(permission);

    }

}
