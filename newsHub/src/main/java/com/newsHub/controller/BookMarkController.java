package com.newsHub.controller;

import com.newsHub.exception.ApiResponse;
import com.newsHub.service.BookmarkService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/bookmarks")
public class BookMarkController {
    BookmarkService bookmarkService;

    @PostMapping
    ApiResponse<Object> createRequest(Object request){
        return null;
    }
}
