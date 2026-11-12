package com.newsHub.Repository;

import com.newsHub.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    boolean existsBySlug(String slug);
    Optional<Article> findBySlug(String slug);
}
