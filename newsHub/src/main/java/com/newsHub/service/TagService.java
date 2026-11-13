package com.newsHub.service;

import com.newsHub.Repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TagService {
    TagRepository tagRepository;

    public Object createRequest(Object request){
        return null;
    }
}
