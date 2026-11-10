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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ArticleService {
    ArticleRepository articleRepository;
    ArticleMapper articleMapper;
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ArticleResponse createRequest(ArticleRequest request){
        Article article = articleMapper.toArticle(request);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        article.setUser(user);
        article.setArticleStatus(ArticleStatus.DRAFT);

        return articleMapper.toArticleResponse(articleRepository.save(article));
    }
}
