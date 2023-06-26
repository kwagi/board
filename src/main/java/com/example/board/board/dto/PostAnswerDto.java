package com.example.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostAnswerDto {
    private String writer;
    @NotBlank(message = "내용을 입력하세요")
    private String answerContents;
}
