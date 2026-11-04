package com.newsHub.controller;

import com.newsHub.dto.request.RoleRequest;
import com.newsHub.dto.response.RoleResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/roles")
@RestController
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> roleRequest(RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .message("Role thành công")
                .result(roleService.roleRequest(request))
                .build();
    }
}
