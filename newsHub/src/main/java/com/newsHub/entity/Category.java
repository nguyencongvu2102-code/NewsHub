package com.newsHub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column(nullable = false,unique = true,length = 100)
    private String name;

    @Column(nullable = false,unique = true,length = 120)
    private String slug;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @Builder.Default
    Set<Article> articles = new HashSet<>();
}
