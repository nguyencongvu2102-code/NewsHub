package com.newsHub.service;

import com.newsHub.Repository.RoleRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.UpdatePassword;
import com.newsHub.dto.request.UpdateRoleUserRequest;
import com.newsHub.dto.request.UserRequest;
import com.newsHub.dto.request.UserUpdateRequest;
import com.newsHub.dto.response.UserResponse;
import com.newsHub.entity.Role;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Transactional
    public UserResponse request(UserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Role role = roleRepository.findByName("VIEWER").orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );

        User user = userMapper.toUser(request);
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<UserResponse> getAll(){

        return userRepository.findByUsers().stream().map(
                userMapper::toUserResponse
        ).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public UserResponse getUser(Integer userId){
        User user = userRepository.findByUserId(userId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(
                        ()-> new AppException(ErrorCode.USER_NOT_FOUND)
                );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        user.setIsDeleted(true);
        userRepository.save(user);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse updateUser(Integer userId, UserUpdateRequest request){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if(!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        userMapper.toUpdate(user,request);

        if (request.getPassWord() != null
                && !request.getPassWord().isBlank()) {

            user.setPassWord(
                    passwordEncoder.encode(request.getPassWord())
            );
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse updateRole(Integer userId, UpdateRoleUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Role role = roleRepository.findByName(request.getRoleName()).orElseThrow(
                ()-> new AppException(ErrorCode.ROLE_NOT_FOUND)
        );

        user.setRoles(new HashSet<>(Set.of(role)));

        return userMapper.toUserResponse(user);
    }


    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();


        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }



        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<UserResponse> getIsDeletedFalse(){
        return userRepository.findByIsDeletedFalse().stream()
                .map(userMapper::toUserResponse).toList();
    }


    @PreAuthorize("hasRole('ADMIN','VIEWER','EDITOR')")
    @Transactional
    public UserResponse updatePassword(Integer userId, UpdatePassword password){
        User user = userRepository.findByUserId(userId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!user.getEmail().equals(email)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if(!passwordEncoder.matches(password.getOldPassWord(), user.getPassWord())){
            throw new AppException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        if(!password.getNewPassWord().equals(password.getConfirmPassword())){
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if(passwordEncoder.matches(password.getNewPassWord(), user.getPassWord())){
            throw new AppException(ErrorCode.PASSWORD_SAME_AS_OLD);
        }

        user.setPassWord(passwordEncoder.encode(password.getNewPassWord()));

        return userMapper.toUserResponse(user);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse restoreUser(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        user.setIsDeleted(false);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<UserResponse> getDeleteUsers(){
        return userRepository.findDeletedUsers().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }



}
