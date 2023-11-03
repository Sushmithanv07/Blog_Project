package com.myblog8.entity;

import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(
        name = "posts" , uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
      )
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable=false)
    private String title;
    @Column(name="description", nullable=false)
    private String description;
    @Column(name="content", nullable=false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //cascade ---> if we remove post the comment should also be removed
    List<Comment> comments = new ArrayList<>();                                   // orphanRemoval ---> if parent is removed the child should also be removed

}

