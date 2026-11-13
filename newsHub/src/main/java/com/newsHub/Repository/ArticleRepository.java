package com.newsHub.Repository;

import com.newsHub.entity.Article;
import com.newsHub.status.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    boolean existsBySlug(String slug);

    @EntityGraph(attributePaths = "user")
    Optional<Article> findBySlug(String slug);

    @EntityGraph(attributePaths = "user")
    @Query("Select a from Article a where a.articleStatus = :status ")
    Page<Article> findByStatus(@Param("status") ArticleStatus status, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    @Query("select a from Article a where a.user.id = :userId order by a.createdAt DESC")
    Page<Article> findByMyArticles(@Param("userId") Integer userId,Pageable pageable);


    @EntityGraph(attributePaths = "user")
    @Query("select a from Article a where a.id = :articleId")
    Optional<Article> findByArticleById(@Param("articleId") Integer articleId);
}
