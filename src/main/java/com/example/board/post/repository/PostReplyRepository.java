package com.example.board.post.repository;

import com.example.board.post.entity.PostReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReplyRepository extends JpaRepository<PostReply, Long> {
}
