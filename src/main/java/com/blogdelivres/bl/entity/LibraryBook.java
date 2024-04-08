package com.blogdelivres.bl.entity;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryBookId;

    @ManyToOne()
    @JoinColumn(name = "libraryId")
    private Library library;

    @ManyToOne()
    @JoinColumn(name = "bookId")
    private Book book;
}
