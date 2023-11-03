package com.myblog8.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.ManyToOne;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
    private String email;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)  // post_id is a foreign key through which we are joining tables
    private Post post;

}
