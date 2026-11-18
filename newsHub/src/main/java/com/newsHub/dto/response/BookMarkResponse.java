package com.newsHub.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMarkResponse {

    private Integer id;

    private Integer userId;
    private String userName;

    private Integer articleId;
    private String articleTitle;
    private String articleSlug;
    private String thumbnailUrl;

    private LocalDateTime createdAt;
}