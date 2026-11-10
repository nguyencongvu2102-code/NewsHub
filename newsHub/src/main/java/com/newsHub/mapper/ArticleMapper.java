package com.newsHub.mapper;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.entity.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toArticle(ArticleRequest request);
    ArticleResponse toArticleResponse(Article article);
}
