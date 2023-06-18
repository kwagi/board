package com.example.board.post.entity;

import com.example.board.member.entity.Member;
import com.example.board.post.enums.Status;
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
    private Long          id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member        member;
    // poster 는 member 이메일
    private String        poster;
    private String        title;
    private String        contents;
    private Status        status;
    private long          hits;
    private long          likes;
    private LocalDateTime writtenDate;
}
