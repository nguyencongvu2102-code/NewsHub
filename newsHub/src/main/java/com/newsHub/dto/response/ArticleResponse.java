package com.newsHub.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private Integer id;
    private String title;
    private String summary;
    private String content;
    private String thumbnailUrl;
    private AuthorResponse authorResponse;
    private LocalDateTime publishedAt;
}
