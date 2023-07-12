package com.example.board.board.repository;

import com.example.board.board.entity.Post;
import com.example.board.board.entity.Reply;
import com.example.board.board.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPostAndPostReplyStatus(Post post, PostStatus status);
}
