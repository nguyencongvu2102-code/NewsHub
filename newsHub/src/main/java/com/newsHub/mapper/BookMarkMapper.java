package com.newsHub.mapper;

import com.newsHub.dto.request.BookMarkRequest;
import com.newsHub.dto.response.BookMarkResponse;
import com.newsHub.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMarkMapper {
    Bookmark toBookmark(BookMarkRequest bookMarkRequest);
    BookMarkResponse toBookMarkResponse(Bookmark bookmark);
}
