package com.example.board.board.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostAnswerDto {
    private String writer;
    private String answerContents;
}
