package com.newsHub.dto.request;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PermissionRequest {
    private String name;
    private String description;
}
