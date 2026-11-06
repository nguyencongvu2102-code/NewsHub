package com.newsHub.controller;

import com.newsHub.dto.request.PermissionRequest;
import com.newsHub.dto.request.UpdatePermissionRequest;
import com.newsHub.dto.response.PermissionResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> requestPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .message("Permission thành công")
                .result(permissionService.requestPermission(request))
                .build();
    }


    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(1000)
                .message("Lấy permissions thành công")
                .result(permissionService.getPermissions())
                .build();
    }

    @GetMapping("/{permissionId}")
    ApiResponse<PermissionResponse> getPermission(@PathVariable Integer permissionId){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .message("PermissionId thành công")
                .result(permissionService.getPermission(permissionId))
                .build();
    }


    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> delete(@PathVariable Integer permissionId){
        permissionService.delete(permissionId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("xóa thành công")
                .build();

    }

    @PutMapping("/{permissionId}")
    ApiResponse<PermissionResponse> updatePermission(@PathVariable Integer permissionId, @RequestBody UpdatePermissionRequest request){

        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .message("Update permission thành công")
                .result(permissionService.updatePermission(request,permissionId))
                .build();
    }
}
