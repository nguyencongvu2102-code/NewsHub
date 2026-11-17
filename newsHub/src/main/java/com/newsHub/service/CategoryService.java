package com.newsHub.service;

import com.newsHub.Repository.ArticleRepository;
import com.newsHub.Repository.CategoryRepository;
import com.newsHub.dto.request.CategoryRequest;
import com.newsHub.dto.request.UpdateCategory;
import com.newsHub.dto.response.CategoryResponse;
import com.newsHub.entity.Category;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.CategoryMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    ArticleRepository articleRepository;

    @Transactional
    public CategoryResponse createRequest(CategoryRequest categoryRequest){
        if(categoryRepository.existsBySlug(categoryRequest.getSlug())){
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Category category = categoryMapper.toCategory(categoryRequest);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getCategories(Pageable pageable){
        return categoryRepository.findByAll(pageable)
                .map(categoryMapper::toCategoryResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Integer categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional
    public void deleteCategory(Integer categoryId){

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        if(articleRepository.existsByCategory_Id(categoryId)){
            throw new AppException(ErrorCode.CATEGORY_IN_USE);
        }
        categoryRepository.delete(category);
    }

    @Transactional
    public CategoryResponse updateCategory(UpdateCategory updateCategory,Integer categoryId){
        if(updateCategory.getSlug() != null &&
                categoryRepository.existsBySlugAndIdNot(updateCategory.getSlug(), categoryId)){
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        if(updateCategory.getName() != null && categoryRepository.existsByNameAndIdNot(
                updateCategory.getName(), categoryId
        )){
            throw new AppException(ErrorCode.CATEGORY_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        categoryMapper.updateCategory(category,updateCategory);
        return categoryMapper.toCategoryResponse(category);



    }


    @Transactional(readOnly = true)
    public CategoryResponse getCategorySlug(String slug){
        Category category = categoryRepository.findBySlug(slug).orElseThrow(
                ()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND)
        );


        return categoryMapper.toCategoryResponse(category);
    }

}
