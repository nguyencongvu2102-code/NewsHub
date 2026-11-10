package com.newsHub.entity;

import com.newsHub.status.ArticleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer id;
    @Column(nullable = false,length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;
    private String summary;
    private String content;
    @Column(name = "thumbnail_url",length = 500)
    private String thumbnailUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "article_status",nullable = false)
    private ArticleStatus articleStatus = ArticleStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "create_at",updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
