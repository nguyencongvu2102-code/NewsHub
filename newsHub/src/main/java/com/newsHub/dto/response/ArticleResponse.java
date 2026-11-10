package com.newsHub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
}
