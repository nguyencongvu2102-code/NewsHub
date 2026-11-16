package com.newsHub.Repository;

import com.newsHub.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
    boolean existsBySlug(String slug);
    boolean existsByName(String name);

    @Query("""
            Select case when count(a) > 0 then true
            else false
            end
            from Article a join a.tags t
            where t.id = :tagId
            """)
    boolean existsByArticlesContaining(@Param("tagId") Integer tagId);

}
