package com.newsHub.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(1001,"email already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1002,"User not found",HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(2001,"Permission not null",HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(3001,"Role not found",HttpStatus.NOT_FOUND),
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
