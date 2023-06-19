package com.example.board.post.repository;

import com.example.board.member.entity.Member;
import com.example.board.post.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    long countByPostLikesIdAndMember(Long id, Member member);
}
