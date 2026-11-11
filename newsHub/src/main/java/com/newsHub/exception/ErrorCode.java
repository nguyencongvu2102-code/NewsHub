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
    KEY_ERROR(999,"KEY ERROR",HttpStatus.INTERNAL_SERVER_ERROR),


    EMAIL_ALREADY_EXISTS(1001,"email already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND(1002,"User not found",HttpStatus.NOT_FOUND),
    PASS_WORD(1003,"password not null",HttpStatus.BAD_REQUEST),
    PASSWORD(1004,"The password must be between {min} and {max} characters long and include uppercase letters," +
            " lowercase letters, numbers, and special characters.",HttpStatus.BAD_REQUEST),
    EMAIL_REQUIRED(1005, "Email cannot be blank", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1006, "Invalid email format", HttpStatus.BAD_REQUEST),
    DISPLAY_NAME_REQUIRED(1007, "Display name cannot be blank",HttpStatus.BAD_REQUEST),
    DISPLAY_NAME_SIZE(1008, "Display name must contain 2 to 50 characters", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INCORRECT(1009, "Old password is incorrect", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1010, "New password and confirm password do not match", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD(1011, "New password must be different from old password", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_REQUIRED(1012, "Old password is required", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_REQUIRED(1013, "New password is required", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_REQUIRED(1014, "Confirm password is required", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1015, "You do not have permission to change this password", HttpStatus.FORBIDDEN),


//    Permission
    PERMISSION_NOT_FOUND(2001,"Permission not null",HttpStatus.NOT_FOUND),
    PERMISSION_EXISTED(2002,"Permission already exists",HttpStatus.CONFLICT),
    PERMISSION_IN_USE(2003, "Permission is currently assigned to a role", HttpStatus.CONFLICT),





//    ROLE
    ROLE_NOT_FOUND(3001,"Role not found",HttpStatus.NOT_FOUND),
    ROLE_EXISTS(3002,"Role name and id already exists",HttpStatus.CONFLICT),
    ROLE_BLANK(3003,"The role field cannot be left blank",HttpStatus.BAD_REQUEST),
    ROLE_IN_USE(3004, "Role is being used by users", HttpStatus.CONFLICT),
    ROLE_CANNOT_DELETE(3005, "System roles cannot be deleted", HttpStatus.FORBIDDEN),
    ROLE_CANNOT_RENAME(3006,"System roles cannot be rename",HttpStatus.FORBIDDEN),


    AUTHENTICATED(4001,"UNAUTHENTICATED",HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(4002,"UNAUTHENTICATED",HttpStatus.UNAUTHORIZED),


//    Article
    ARTICLE_SLUG(5001,"Article slug not found",HttpStatus.NOT_FOUND),
    ARTICLE_NOT_FOUND(5002,"Article not found",HttpStatus.NOT_FOUND),

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
