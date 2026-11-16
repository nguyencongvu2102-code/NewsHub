package com.newsHub.controller;

import com.newsHub.dto.request.TagRequest;
import com.newsHub.dto.request.UpdateTag;
import com.newsHub.dto.response.TagResponse;
import com.newsHub.exception.ApiResponse;
import com.newsHub.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping
    ApiResponse<TagResponse> createTags(@RequestBody TagRequest tagRequest){
        return ApiResponse.<TagResponse>builder()
                .code(1000)
                .message("Tags thành công")
                .result(tagService.createRequest(tagRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<TagResponse>> getTags(){
        return ApiResponse.<List<TagResponse>>builder()
                .code(1000)
                .message("Get tags thành công")
                .result(tagService.getTags())
                .build();
    }

    @GetMapping("/{tagId}")
    ApiResponse<TagResponse> getTag(@PathVariable Integer tagId){
        return ApiResponse.<TagResponse>builder()
                .code(1000)
                .message("Get tag thành công")
                .result(tagService.getTagById(tagId))
                .build();
    }

    @DeleteMapping("/{tagId}")
    ApiResponse<Void> delete(@PathVariable Integer tagId){
        tagService.deleteTag(tagId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa thành công").build();
    }

    @GetMapping("/slug/{slug}")
    ApiResponse<TagResponse> getTagSlug(@PathVariable String slug){
        return ApiResponse.<TagResponse>builder()
                .code(1000)
                .message("Slug thành công")
                .result(tagService.getTagSlug(slug))
                .build();
    }

    @PatchMapping("/{tagId}")
    ApiResponse<TagResponse> updateTag(@RequestBody UpdateTag tag,@PathVariable Integer tagId){
        return ApiResponse.<TagResponse>builder()
                .code(1000)
                .message("Update tag thành công")
                .result(tagService.updateTag(tag,tagId))
                .build();
    }


}
