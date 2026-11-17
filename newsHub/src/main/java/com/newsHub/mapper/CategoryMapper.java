package com.newsHub.mapper;

import com.newsHub.dto.request.CategoryRequest;
import com.newsHub.dto.request.UpdateCategory;
import com.newsHub.dto.response.CategoryResponse;
import com.newsHub.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest categoryRequest);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, UpdateCategory updateCategory);
}
