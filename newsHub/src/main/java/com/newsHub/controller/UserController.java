package com.newsHub.controller;

import com.newsHub.dto.request.UpdatePassword;
import com.newsHub.dto.request.UpdateRoleUserRequest;
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
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Get users success")
                .result(userService.getAll())
                .build();
    }

    @DeleteMapping("deleteUser/{userId}")
    ApiResponse<Void> delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Account deleted successfully")
                .build();
    }

    @PutMapping("updateUser/{userId}")
    ApiResponse<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest request,
                                         @PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update success")
                .result(userService.updateUser(userId,request)).build();
    }


    @GetMapping("/getMyInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Get myInfo thành công")
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/updateRole/{userId}")
    ApiResponse<UserResponse> updateRole(@PathVariable Integer userId,
                                         @RequestBody UpdateRoleUserRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update role thành công")
                .result(userService.updateRole(userId,request))
                .build();
    }


    @GetMapping("/isDeletedFalse")
    ApiResponse<List<UserResponse>> getIsDeleteFalse(){
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Get isDeleteFalse thành công")
                .result(userService.getIsDeletedFalse())
                .build();
    }

    @GetMapping("getUser/{userId}")
    ApiResponse<UserResponse> getUsers(@PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Get userId thành công")
                .result(userService.getUser(userId))
                .build();
    }


    @PatchMapping("/passWord/{userId}")
    ApiResponse<UserResponse> updatePassword(@PathVariable Integer userId, @RequestBody UpdatePassword password){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update passWord thành công")
                .result(userService.updatePassword(userId,password))
                .build();
    }


    @PatchMapping("/restoreUser/{userId}")
    ApiResponse<UserResponse> restoreUser(@PathVariable Integer userId){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Khôi phục thành công")
                .result(userService.restoreUser(userId))
                .build();
    }

    @GetMapping("/getDeleteUsers")
    ApiResponse<List<UserResponse>> getDeleteUsers(){
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Get delete user thành công")
                .result(userService.getDeleteUsers())
                .build();
    }







}
