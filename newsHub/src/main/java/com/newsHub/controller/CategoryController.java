package com.newsHub.controller;

import com.newsHub.dto.request.CategoryRequest;
import com.newsHub.dto.request.UpdateCategory;
import com.newsHub.dto.response.CategoryResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/categories")
@RestController
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> createRequest(@Valid @RequestBody CategoryRequest categoryRequest){
        return ApiResponse.<CategoryResponse>builder()
                .code(1000)
                .message("Category thành công")
                .result(categoryService.createRequest(categoryRequest))
                .build();
    }

    @GetMapping
    ApiResponse<Page<CategoryResponse>> getAll(Pageable pageable){
        return ApiResponse.<Page<CategoryResponse>>builder()
                .code(1000)
                .message("Get categories thành công")
                .result(categoryService.getCategories(pageable)).build();
    }

    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> getCategory(@PathVariable Integer categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .code(1000)
                .message("Get category thành công")
                .result(categoryService.getCategory(categoryId)).build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<Void> delete(@PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Delete thành công").build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@Valid @RequestBody UpdateCategory updateCategory,
                                                 @PathVariable Integer categoryId){
        return ApiResponse.<CategoryResponse>builder()
                .code(1000)
                .message("Update category thành công")
                .result(categoryService.updateCategory(updateCategory, categoryId))
                .build();
    }

    @GetMapping("/slug/{slug}")
    ApiResponse<CategoryResponse> getSlug(@PathVariable String slug){
        return ApiResponse.<CategoryResponse>builder()
                .code(1000)
                .message("Get category slug thành công")
                .result(categoryService.getCategorySlug(slug))
                .build();
    }
}
