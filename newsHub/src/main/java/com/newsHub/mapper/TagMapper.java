package com.newsHub.mapper;

import com.newsHub.dto.request.TagRequest;
import com.newsHub.dto.response.TagResponse;
import com.newsHub.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toTag(TagRequest tagRequest);
    TagResponse toTagResponse(Tag tag);

}
