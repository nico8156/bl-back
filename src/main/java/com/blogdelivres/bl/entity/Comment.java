package com.blogdelivres.bl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "id")
    private UserBis user;

    @ManyToOne
    @JoinColumn(name = "googleId")
    private Book book;

    private String title;
    private String content;
    private Long parentId;
    private Date createdAt;
}
