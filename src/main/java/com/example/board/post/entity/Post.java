package com.example.board.post.entity;

import com.example.board.post.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long          postId;
    private String        poster;
    private String        title;
    private String        contents;
    @Enumerated(EnumType.STRING)
    private PostStatus    postStatus;
    private long          hits;
    private long          likes;
    private LocalDateTime postDate;
    private LocalDateTime deleteDate;
}
