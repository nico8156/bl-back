package com.blogdelivres.bl.Services;

import com.blogdelivres.bl.dto.*;
import com.blogdelivres.bl.entity.Book;
import com.blogdelivres.bl.entity.Comment;
import com.blogdelivres.bl.entity.UserBis;
import com.blogdelivres.bl.enums.UserRole;

public final class TestData {
    public static Book testBook() {
        return Book.builder()
                .googleId("xaxaxaxxa")
                .title("testBook")
                .authors("testMan")
                .publisher("springTest")
                .description("this is a test to create a fake book")
                .pageCount(456)
                .imageLinks("this is an url for a photo thumbnail")
                .build();
    }
    public static BookToSaveRequest bookToSaveRequest() {
        return BookToSaveRequest.builder()
                .googleId("xaxaxaxxa")
                .title("testBook")
                .authors("testMan")
                .publisher("springTest")
                .description("this is a test to create a fake book")
                .pageCount(456)
                .imageLinks("this is an url for a photo thumbnail")
                .build();

    }
    public static BookDTO bookDTO() {
        return BookDTO.builder()
                .googleId("xaxaxaxxa")
                .title("testBook")
                .authors("testMan")
                .publisher("springTest")
                .description("this is a test to create a fake book")
                .pageCount(456)
                .imageLinks("this is an url for a photo thumbnail")
                .build();
    }
    public static UserBis user() {
        return UserBis.builder()
                .photo("ceic est un URL vers une photo")
                .userRole(UserRole.USER)
                .username("testeur")
                .build();
    }
}
