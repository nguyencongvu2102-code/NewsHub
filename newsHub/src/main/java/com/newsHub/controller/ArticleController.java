package com.newsHub.controller;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.request.UpdateArticle;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.ArticleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ArticleController {
    ArticleService articleService;

    @PostMapping
    ApiResponse<ArticleResponse> createRequest(@RequestBody ArticleRequest articleRequest){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Article thành công")
                .result(articleService.createRequest(articleRequest))
                .build();
    }

    @GetMapping("/{slug}")
    ApiResponse<ArticleResponse> getSlug(@PathVariable String slug){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Get slugs thành công")
                .result(articleService.getSlugs(slug))
                .build();
    }

    @PutMapping("/PUBLISHED/{articleId}")
    ApiResponse<ArticleResponse> updatePUBLISHED(@PathVariable Integer articleId){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Update PUBLISHED thành công")
                .result(articleService.updatePUBLISHED(articleId))
                .build();
    }

    @PutMapping("/PENDING/{articleId}")
    ApiResponse<ArticleResponse> updatePENDING(@PathVariable Integer articleId){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Update PENDING thành công")
                .result(articleService.updatePENDING(articleId))
                .build();
    }

    @PutMapping("/REJECTED/{articleId}")
    ApiResponse<ArticleResponse> updateREJECTED(@PathVariable Integer articleId){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Update REJECTED thành công")
                .result(articleService.updateREJECTED(articleId))
                .build();
    }


    @GetMapping("/PUBLISHED/PublishedArticles")
    ApiResponse<Page<ArticleResponse>> getPublishedArticles(Pageable pageable){
        return ApiResponse.<Page<ArticleResponse>>builder()
                .code(1000)
                .message("Get PublishedArticles thành công")
                .result(articleService.getPublishedArticles(pageable))
                .build();
    }

    @GetMapping("/PENDING/PendingArticles")
    ApiResponse<Page<ArticleResponse>> getPendingArticles(Pageable pageable){
        return ApiResponse.<Page<ArticleResponse>>builder()
                .code(1000)
                .message("Get PendingArticles thành công")
                .result(articleService.getPendingArticles(pageable))
                .build();
    }

    @PatchMapping("/UPDATE_ARTICLE/{articleId}")
    ApiResponse<ArticleResponse> updateArticle(@RequestBody UpdateArticle article,
                                               @PathVariable Integer articleId ){
        return ApiResponse.<ArticleResponse>builder().code(1000)
                .message("Update article thành công")
                .result(articleService.updateArticle(article,articleId)).build();
    }

    @DeleteMapping("/deleteArticle/{articleId}")
    ApiResponse<Void> deleteArticle(@PathVariable Integer articleId ){
        articleService.deleteArticle(articleId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Delete article thành công").build();
    }

    @GetMapping("/MyArticles")
    ApiResponse<Page<ArticleResponse>> getMyArticles(Pageable pageable){
        return ApiResponse.<Page<ArticleResponse>>builder()
                .code(1000)
                .message("Get MyArticles thành công")
                .result(articleService.getMyArticles(pageable))
                .build();
    }

    @GetMapping("/Articles/{articleId}")
    ApiResponse<ArticleResponse> getArticleById(@PathVariable Integer articleId){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Get articleId thành công")
                .result(articleService.getArticleById(articleId)).build();
    }


}
