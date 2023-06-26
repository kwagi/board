package com.example.board.board.repository;

import com.example.board.board.entity.Image;
import com.example.board.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPost(Post post);
}
