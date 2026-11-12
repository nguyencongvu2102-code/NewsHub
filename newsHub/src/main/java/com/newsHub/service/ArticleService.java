package com.newsHub.service;

import com.newsHub.Repository.ArticleRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.entity.Article;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.ArticleMapper;
import com.newsHub.status.ArticleStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ArticleService {
    ArticleRepository articleRepository;
    ArticleMapper articleMapper;
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('ARTICLE_CREATE')")
    @Transactional
    public ArticleResponse createRequest(ArticleRequest request){
        if(articleRepository.existsBySlug(request.getSlug())){
            throw new AppException(ErrorCode.ARTICLE_SLUG_ALREADY_EXISTS);
        }



        Article article = articleMapper.toArticle(request);


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }


        article.setUser(user);
        article.setArticleStatus(ArticleStatus.DRAFT);
        article.setPublishedAt(null);

        return articleMapper.toArticleResponse(articleRepository.save(article));
    }




    @Transactional(readOnly = true)
    public ArticleResponse getSlugs(String slug){
        Article article = articleRepository.findBySlug(slug).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_SLUG)
        );

        if(article.getArticleStatus() != ArticleStatus.PUBLISHED){
            throw new AppException(ErrorCode.ARTICLE_NOT_FOUND);
        }

        return articleMapper.toArticleResponse(article);
    }

    @PreAuthorize("hasAuthority('ARTICLE_PUBLISH')")
    @Transactional
    public ArticleResponse updatePUBLISHED(Integer articleId){

        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if(article.getArticleStatus() != ArticleStatus.PENDING){
            throw new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }

        article.setArticleStatus(ArticleStatus.PUBLISHED);
        article.setPublishedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));

        return articleMapper.toArticleResponse(article);
    }

    @PreAuthorize("hasAuthority('ARTICLE_SUBMIT')")
    @Transactional
    public ArticleResponse updatePENDING(Integer articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        if(!article.getUser().getEmail().equals(email)){
            throw new  AppException(ErrorCode.UNAUTHORIZED);
        }

        if(article.getArticleStatus() != ArticleStatus.DRAFT){
            throw new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }


        article.setArticleStatus(ArticleStatus.PENDING);
        article.setPublishedAt(null);

        return articleMapper.toArticleResponse(article);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ArticleResponse updateREJECTED(Integer articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if(article.getArticleStatus() != ArticleStatus.PENDING){
            throw new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }

        article.setPublishedAt(null);
        article.setArticleStatus(ArticleStatus.REJECTED);
        return articleMapper.toArticleResponse(article);
    }

}
