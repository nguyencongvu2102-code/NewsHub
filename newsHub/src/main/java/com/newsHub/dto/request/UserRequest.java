package com.newsHub.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String email;
    String passWord;
    String disPlayName;
    LocalDateTime createdAt;
    Boolean isDeleted;
}
