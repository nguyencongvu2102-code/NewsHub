package com.newsHub.service;

import com.newsHub.Repository.ArticleRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.request.UpdateArticle;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


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

        if (article.getArticleStatus() != ArticleStatus.DRAFT &&
                article.getArticleStatus() != ArticleStatus.REJECTED) {

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


//    Chỉnh sửa bài viết
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ARTICLE_UPDATE')")
    @Transactional
    public ArticleResponse updateArticle(UpdateArticle updateArticle,Integer articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role ->
                "ADMIN".equals(role.getName()));

        boolean isOwner = article.getUser().getId().equals(user.getId());

        if (!isAdmin && !isOwner) {
            throw new AppException(ErrorCode.ARTICLE_ACCESS_DENIED);
        }

        if (!isAdmin &&
                article.getArticleStatus() != ArticleStatus.DRAFT &&
                article.getArticleStatus() != ArticleStatus.REJECTED) {

            throw new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }



        articleMapper.updateArticle(article,updateArticle);
        article.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        return articleMapper.toArticleResponse(article);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteArticle(Integer articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if(article.getArticleStatus() == ArticleStatus.PUBLISHED){
            throw  new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }

        articleRepository.delete(article);

    }

    @Transactional(readOnly = true)
    public Page<ArticleResponse> getPublishedArticles(Pageable pageable){
        return articleRepository.findByStatus(ArticleStatus.PUBLISHED,pageable).map(
                articleMapper::toArticleResponse
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getMyArticles(Pageable pageable){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException( ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Article> articles = articleRepository.findByMyArticles(user.getId(),pageable);
        return articles.map(articleMapper::toArticleResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getPendingArticles(Pageable pageable){
        return articleRepository.findByStatus(ArticleStatus.PENDING,pageable).map(
                articleMapper::toArticleResponse
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Integer articleId){
        var article = articleRepository.findByArticleById(articleId).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        return articleMapper.toArticleResponse(article);
    }









}
