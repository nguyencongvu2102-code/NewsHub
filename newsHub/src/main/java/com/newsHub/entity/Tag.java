package com.newsHub.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;
    @Column(length = 50,nullable = false,unique = true)
    private String name;
    @Column(length = 60,nullable = false,unique = true)
    private String slug;

    @Builder.Default
    @ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
    private Set<Article> articles = new HashSet<>();

}
