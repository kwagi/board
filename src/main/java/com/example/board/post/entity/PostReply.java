package com.example.board.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   postReplyId;
    private String writer;
    private String        replyContents;
    private LocalDateTime replyDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
