package com.newsHub.controller;

import com.newsHub.dto.request.AuthenticationRequest;
import com.newsHub.dto.request.IntrospectRequest;
import com.newsHub.dto.request.LogOutRequest;
import com.newsHub.dto.request.RefreshTokenRequest;
import com.newsHub.dto.response.AuthenticationResponse;
import com.newsHub.dto.response.IntrospectResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> logIn(@RequestBody AuthenticationRequest authenticationRequest){
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .message("logIn thành công")
                .result(authenticationService.createAuthentication(authenticationRequest)).build();
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .message("Introspect thành công")
                .result(authenticationService.introspectResponse(introspectRequest)).build();
    }

    @PostMapping("/logOut")
    ApiResponse<Void> logOut(@RequestBody LogOutRequest request) throws ParseException, JOSEException {
        authenticationService.logOut(request);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("LogOut thành công").build();
    }


    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .message("Refresh thành công")
                .result(authenticationService.reFresh(request)).build();
    }

}
