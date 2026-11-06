package com.newsHub.service;

import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.UserRequest;
import com.newsHub.dto.request.UserUpdateRequest;
import com.newsHub.dto.response.UserResponse;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse request(UserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = userMapper.toUser(request);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll(){

        return userRepository.findAll().stream().map(
                userMapper::toUserResponse
        ).toList();
    }

    @Transactional
    public void delete(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(
                        ()-> new AppException(ErrorCode.USER_NOT_FOUND)
                );
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Transactional
    public UserResponse updateUser(Integer userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.toUpdate(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
