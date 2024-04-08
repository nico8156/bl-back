package com.blogdelivres.bl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long libraryId;

    @ManyToOne
    @JoinColumn(name = "id")
    private UserBis user;
    private String libraryName;
}


