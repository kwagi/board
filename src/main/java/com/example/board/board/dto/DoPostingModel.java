package com.example.board.board.dto;

import com.example.board.board.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoPostingModel {
    private String title;
    private String contents;
    private String     poster;
    private PostStatus postStatus;
}
