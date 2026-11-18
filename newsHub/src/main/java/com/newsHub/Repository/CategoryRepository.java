package com.newsHub.Repository;

import com.newsHub.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsBySlug(String slug);
    Optional<Category> findBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug,Integer categoryId);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name,Integer categoryId);
    Page<Category> findAll(Pageable pageable);


}
