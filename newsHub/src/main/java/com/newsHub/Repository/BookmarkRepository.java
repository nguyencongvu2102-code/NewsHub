package com.newsHub.Repository;

import com.newsHub.entity.Article;
import com.newsHub.entity.Bookmark;
import com.newsHub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {
    boolean existsByUserAndArticle(User user, Article article);
    Page<Bookmark> findAllByUser_Id(
            Integer userId,
            Pageable pageable
    );
    @EntityGraph(attributePaths = "article")
    Optional<Bookmark> findByIdAndUser_Id(
            Integer bookMarkId,
            Integer userId
    );
}
