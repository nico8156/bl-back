package com.blogdelivres.bl.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDTO {

    private String googleId;
    private String title;
    private String authors;
    private String publisher;
    private String description;
    private long pageCount;
    private String imageLinks;

}
