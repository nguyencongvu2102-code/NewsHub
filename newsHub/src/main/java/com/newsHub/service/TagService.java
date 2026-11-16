package com.newsHub.service;

import com.newsHub.Repository.TagRepository;
import com.newsHub.dto.request.TagRequest;
import com.newsHub.dto.response.TagResponse;
import com.newsHub.entity.Tag;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.TagMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TagService {
    TagRepository tagRepository;
    TagMapper tagMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TagResponse createRequest(TagRequest request){
        if (tagRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.TAG_ALREADY_EXISTS);
        }

        if(tagRepository.existsBySlug(request.getSlug())){
            throw new AppException(ErrorCode.TAG_ALREADY_EXISTS);
        }
        Tag tag = tagMapper.toTag(request);
        tag = tagRepository.save(tag);

        return tagMapper.toTagResponse(tag);
    }


    @Transactional(readOnly = true)
    public List<TagResponse> getTags(){
        return tagRepository.findAll().stream()
                .map(tagMapper::toTagResponse).toList();
    }

    @Transactional(readOnly = true)
    public TagResponse getTagById(Integer tagId) {

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.TAG_NOT_FOUND)
                );

        return tagMapper.toTagResponse(tag);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteTag(Integer tagId){
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                ()-> new AppException(ErrorCode.TAG_NOT_FOUND)
        );

        if(tagRepository.existsByArticlesContaining(tagId)){
            throw new AppException(ErrorCode.TAG_IN_USE);
        }

        tagRepository.delete(tag);
    }

}
