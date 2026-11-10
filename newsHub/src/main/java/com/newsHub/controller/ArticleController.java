package com.newsHub.controller;

import com.newsHub.dto.request.ArticleRequest;
import com.newsHub.dto.response.ArticleResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.ArticleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ArticleController {
    ArticleService articleService;

    @PostMapping
    public ApiResponse<ArticleResponse> createRequest(@RequestBody ArticleRequest articleRequest){
        return ApiResponse.<ArticleResponse>builder()
                .code(1000)
                .message("Article thành công")
                .result(articleService.createRequest(articleRequest))
                .build();
    }
}
