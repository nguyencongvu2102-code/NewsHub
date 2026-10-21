package com.newsHub.controller;

import com.newsHub.dto.request.UserRequest;
import com.newsHub.dto.request.UserUpdateRequest;
import com.newsHub.dto.response.UserResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    ApiResponse<UserResponse> request(@RequestBody UserRequest userRequest){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Register success")
                .result(userService.request(userRequest))
                .build();
    }

    @GetMapping("/me")
    ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Get users success")
                .result(userService.getAll())
                .build();
    }

    @DeleteMapping("/userId")
    ApiResponse<Void> delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Account deleted successfully")
                .build();
    }

    @PutMapping("/userId")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Integer
                                         userId){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update success")
                .result(userService.updateUser(userId,request)).build();
    }



}
