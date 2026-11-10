package com.newsHub.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleRequest {
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String thumbnailUrl;

  
}
