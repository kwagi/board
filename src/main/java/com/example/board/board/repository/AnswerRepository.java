package com.example.board.board.repository;

import com.example.board.board.entity.Answer;
import com.example.board.board.entity.Reply;
import com.example.board.board.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByReplyAndAnswerStatus(Reply reply, PostStatus status);
}
