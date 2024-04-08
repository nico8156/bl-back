package com.blogdelivres.bl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookToSaveRequest {

    private String googleId;
    private String title;
    private String authors;
    private String publisher;
    private String description;
    private long pageCount;
    private String imageLinks;
}
