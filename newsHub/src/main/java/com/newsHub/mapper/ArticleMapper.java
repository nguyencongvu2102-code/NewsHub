package com.newsHub.mapper;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.request.UpdateArticle;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.entity.Article;
import com.newsHub.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "tags",ignore = true)
    Article toArticle(ArticleRequest request);

    @Mapping(source = "user", target = "authorResponse")
    ArticleResponse toArticleResponse(Article article);
    void updateArticle(@MappingTarget Article article, UpdateArticle updateArticle);

//    default String map(Tag tag){
//        return tag == null ? null : tag.getName();
//    }


}
