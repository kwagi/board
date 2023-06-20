package com.example.board.board.entity;

import com.example.board.board.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long          replyId;
    private String        writer;
    private String        replyContents;
    private PostStatus    postReplyStatus;
    private LocalDateTime replyDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
