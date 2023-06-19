package com.example.board.post.entity;

import com.example.board.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_likes_id")
    private Long postLikesId;

    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post   post;
}
