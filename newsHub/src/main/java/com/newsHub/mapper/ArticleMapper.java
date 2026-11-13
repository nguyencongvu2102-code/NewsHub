package com.newsHub.mapper;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.request.UpdateArticle;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toArticle(ArticleRequest request);

    ArticleResponse toArticleResponse(Article article);
    void updateArticle(@MappingTarget Article article, UpdateArticle updateArticle);
}
