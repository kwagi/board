package com.example.board.board.repository;

import com.example.board.board.entity.Post;
import com.example.board.member.entity.Member;
import com.example.board.board.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    long countLikesByPostAndMember(Post post, Member member);
}
