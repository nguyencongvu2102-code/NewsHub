package com.newsHub.controller;

import com.newsHub.dto.request.BookMarkRequest;
import com.newsHub.dto.response.BookMarkResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.BookmarkService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookMarks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BooMarkController {
    BookmarkService bookmarkService;

    @PostMapping
    ApiResponse<BookMarkResponse> createBookMard(@RequestBody BookMarkRequest bookMarkRequest){
        return ApiResponse.<BookMarkResponse>builder()
                .code(1000)
                .message("BookMark thành công")
                .result(bookmarkService.createBookmark(bookMarkRequest)).build();
    }

    @GetMapping
    ApiResponse<Page<BookMarkResponse>> getBookMarks(Pageable pageable){
        return ApiResponse.<Page<BookMarkResponse>>builder()
                .code(1000)
                .message("Get bookMarks thành công")
                .result(bookmarkService.getBookMarks(pageable)).build();
    }

    @GetMapping("/{bookMarkId}")
    ApiResponse<BookMarkResponse> getBookMark(@PathVariable Integer bookMarkId){
        return ApiResponse.<BookMarkResponse>builder()
                .code(1000)
                .message("Get bookMark thành công")
                .result(bookmarkService.getBookMark(bookMarkId))
                .build();
    }

    @DeleteMapping("/{bookMarkId}")
    ApiResponse<Void> deleteBookMark(@PathVariable Integer bookMarkId){
        bookmarkService.deleteBookMark(bookMarkId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Delete bookMark thành công").build();
    }
}
