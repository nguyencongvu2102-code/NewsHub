package com.newsHub.controller;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.ArticleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
}
