package com.newsHub.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UpdateRoleRequest {
    private String name;
    private String description;
    private Set<String> permissions;
}
