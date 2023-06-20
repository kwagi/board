package com.example.board.board.entity;

import com.example.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long likesId;

    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post   post;
}
