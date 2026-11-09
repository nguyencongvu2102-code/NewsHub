package com.newsHub.controller;

import com.newsHub.dto.request.UserRequest;
import com.newsHub.dto.request.UserUpdateRequest;
import com.newsHub.dto.response.UserResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> request(@Valid @RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Register success")
                .result(userService.request(userRequest))
                .build();
    }

    @GetMapping("/me")
    ApiResponse<List<UserResponse>> getAll(){
        var authenticated = SecurityContextHolder.getContext().getAuthentication();
        log.info("email :{}",authenticated.getName());
        authenticated.getAuthorities().stream().forEach(grantedAuthority ->
                log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Get users success")
                .result(userService.getAll())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Account deleted successfully")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest request,
                                         @PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update success")
                .result(userService.updateUser(userId,request)).build();
    }



}
