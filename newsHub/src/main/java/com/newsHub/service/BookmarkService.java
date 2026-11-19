package com.newsHub.service;

import com.newsHub.Repository.ArticleRepository;
import com.newsHub.Repository.BookmarkRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.BookMarkRequest;
import com.newsHub.dto.response.BookMarkResponse;
import com.newsHub.entity.Article;
import com.newsHub.entity.Bookmark;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.newsHub.mapper.BookMarkMapper;
import com.newsHub.status.ArticleStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BookmarkService {
    BookmarkRepository bookmarkRepository;
    BookMarkMapper bookMarkMapper;
    ArticleRepository articleRepository;
    UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public BookMarkResponse createBookmark(BookMarkRequest request){
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(
                ()-> new AppException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        if (article.getArticleStatus() != ArticleStatus.PUBLISHED) {
            throw new AppException(ErrorCode.INVALID_ARTICLE_STATUS);
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        if(bookmarkRepository.existsByUserAndArticle(user,article)){
            throw new AppException(ErrorCode.BOOKMARK_ALREADY_EXISTS);
        }

        Bookmark bookmark = bookMarkMapper.toBookmark(request);
        bookmark.setArticle(article);
        bookmark.setUser(user);
        bookmark = bookmarkRepository.save(bookmark);
        return bookMarkMapper.toBookMarkResponse(bookmark);

    }


    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public Page<BookMarkResponse> getBookMarks(Integer bookMarkId, Pageable pageable){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Page<Bookmark> bookmarks = bookmarkRepository.findAllBookMark(user.getId(),pageable);
        return bookmarks.map(bookMarkMapper::toBookMarkResponse);
    }


    @Transactional(readOnly = true)
    public BookMarkResponse getBookMark(Integer bookMarkId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if(Boolean.TRUE.equals(user.getIsDeleted())){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }


        Bookmark bookmark = bookmarkRepository
                .findByIdAndUser_Id(bookMarkId, user.getId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.BOOKMARK_NOT_FOUND));

        return bookMarkMapper.toBookMarkResponse(bookmark);
    }
}
