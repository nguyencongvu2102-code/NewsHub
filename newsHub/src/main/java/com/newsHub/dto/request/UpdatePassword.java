package com.newsHub.dto.request;

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
public class UpdatePassword {
    @NotBlank(message = "OLD_PASSWORD_REQUIRED")
    String oldPassWord;
    @NotBlank(message = "NEW_PASSWORD_REQUIRED")
    @Size(min = 8,max = 14,message = "DISPLAY_NAME_SIZE")
    String newPassWord;

    @NotBlank(message = "CONFIRM_PASSWORD_REQUIRED")
    String confirmPassword;
}
