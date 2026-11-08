package com.newsHub.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    SERVER_ERROR(9999,"Server error",HttpStatus.INTERNAL_SERVER_ERROR),

    EMAIL_ALREADY_EXISTS(1001,"email already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1002,"User not found",HttpStatus.NOT_FOUND),

//    Permission
    PERMISSION_NOT_FOUND(2001,"Permission not null",HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(2002,"Permission already exists",HttpStatus.CONFLICT),
    PERMISSION_IN_USE(2003, "Permission is currently assigned to a role", HttpStatus.CONFLICT),





//    ROLE
    ROLE_NOT_FOUND(3001,"Role not found",HttpStatus.NOT_FOUND),
    ROLE_EXISTS(3002,"Role name and id already exists",HttpStatus.CONFLICT),
    AUTHENTICATED(4001,"UNAUTHENTICATED",HttpStatus.UNAUTHORIZED)

    ;
    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
