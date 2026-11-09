package com.newsHub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Email(message = "EMAIL_REQUIRED")
    @NotBlank(message = "EMAIL_INVALID")
    String email;
    @NotBlank(message = "PASS_WORD")
    @Size(min = 8,max = 14,message = "PASSWORD")
    String passWord;

    @NotBlank(message = "DISPLAY_NAME_REQUIRED")
    @Size(min = 2,max = 100,message = "DISPLAY_NAME_SIZE")
    String disPlayName;


}
