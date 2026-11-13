package com.newsHub.controller;

import com.newsHub.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TagController {
    TagService tagService;
}
