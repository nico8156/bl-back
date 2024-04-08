package com.blogdelivres.bl.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @Column(unique = true)
    private String googleId;

    private String title;
    private String authors;
    private String publisher;
    @Column(length = 2000)
    private String description;
    private long pageCount;
    @Column(length = 500)
    private String imageLinks;

}
