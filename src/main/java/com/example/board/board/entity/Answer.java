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
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long          answerId;
    private String        writer;
    private String        answerContents;
    @Enumerated(EnumType.STRING)
    private PostStatus    answerStatus;
    private LocalDateTime answerDate;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private Reply reply;
}
