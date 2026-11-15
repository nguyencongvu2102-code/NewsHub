package com.newsHub.Repository;

import com.newsHub.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
    boolean existsBySlug(String slug);
    boolean existsByName(String name);

    boolean existsByArticlesContaining(Tag tag);

}
