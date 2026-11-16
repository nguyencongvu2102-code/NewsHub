package com.newsHub.mapper;

import com.newsHub.dto.request.TagRequest;
import com.newsHub.dto.request.UpdateTag;
import com.newsHub.dto.response.TagResponse;
import com.newsHub.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toTag(TagRequest tagRequest);
    TagResponse toTagResponse(Tag tag);

    void toTag(@MappingTarget Tag tag, UpdateTag updateTag);

}
