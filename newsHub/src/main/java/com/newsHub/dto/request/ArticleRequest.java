package com.newsHub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleRequest {
    @NotBlank(message = "")
    private String title;
    @NotBlank(message = "")
    private String slug;
    @NotBlank(message = "")
    private String summary;
    @NotBlank(message = "")
    private String content;
    @NotBlank(message = "")
    private String thumbnailUrl;
    private Set<Integer> tags;

  
}
